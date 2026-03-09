import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order 
{
    private int id;
    private final OrderItem[] items;
    private final Customer customer;
    private final LocalDateTime createdAt;
    private Discount discount;

    private Order(int id, OrderItem[] items, Customer customer, Discount discount)
    {
        this.id = id;

        OrderItem[] items2 = new OrderItem[items.length];

        for(int i = 0; i < items.length; i++)
        {
            items2[i] = items[i];
        }

        this.items = items2;
        this.customer = customer;
        this.discount = discount;

        createdAt = LocalDateTime.now();
    }

    public int getId()
    {
        return id;
    }

    public Customer getCustomer()
    {
        return customer;
    }

    public Discount getDiscount()
    {
        return discount;
    }

    public void setDiscount(Discount discount)
    {
        this.discount = discount;
    }

    public OrderItem[] getItems()
    {
        OrderItem[] items2 = new OrderItem[items.length];

        for(int i = 0; i < items.length; i++)
        {
            items2[i] = items[i];
        }

        return items2;
    }

    public int getLineCount()
    {
        return items.length;
    }

    public int getItemCount()
    {
        int count = 0;

        for(int i = 0; i < items.length; i++)
        {
            count += items[i].quantity();
        }

        return count;
    }

    public double calculateSubtotal()
    {
        double sum = 0;

        for(int i = 0; i < items.length; i++)
        {
            sum += items[i].totalPrice();
        }

        return sum;
    }

    public double calculateTotal()
    {
        double subtotal = calculateSubtotal();

        if(discount != null)
        {
            double output = discount.apply(subtotal);

            return output;
        }
        else
        {
            return subtotal;
        }
    }

    @Override
    public String toString()
    {
        return String.format("Numer zamowienia: %d, Nazwa klienta: %s, Liczba sztuk: %d, Koncowa wartosc: %.2f", id, customer.name(), getItemCount(), calculateSubtotal());
    }

    public class Receipt
    {
        private final String cashierName;
        private final String CAFE_NAME = "KAWIARNIA \"POD JAVĄ\"";
        private final int WIDTH = 42;

        public Receipt(String cashierName)
        {
            if(cashierName == null | cashierName.isBlank())
            {
                throw new IllegalArgumentException("Imie kasiera nie moze byc puste");
            }

            this.cashierName = cashierName;
        }

        private String center(String text)
        {
            int spacesCount = (WIDTH - text.length()) / 2;

            if(spacesCount < 1)
            {
                return " " + text;
            }

            return " ".repeat(spacesCount) + text;
        }

        private String formatLine(String label, double amount)
        {
            String left = " " + label;
            String right = String.format("%.2f zl", amount);

            int spacesCount = WIDTH - left.length() - right.length();

            if(spacesCount < 1)
            {
                spacesCount = 1;
            }

            return left + " ".repeat(spacesCount) + right;
        }

        private String formatNegLine(String label, double amount)
        {
            String left = " " + label;
            String right = String.format("-%.2f zl", amount);

            int spacesCount = WIDTH - left.length() - right.length();

            if(spacesCount < 1)
            {
                spacesCount = 1;
            }

            return left + " ".repeat(spacesCount) + right;
        }

        public String generate()
        {
            StringBuilder sb = new StringBuilder();

            String temp1 = "=".repeat(WIDTH);
            String temp2 = "-".repeat(WIDTH);

            sb.append(temp1).append("\n");
            sb.append(center(CAFE_NAME)).append("\n");
            sb.append(temp2).append("\n");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            sb.append("Data: ").append(createdAt.format(formatter)).append("\n");
            sb.append("Kasjer: ").append(cashierName).append("\n");
            sb.append("Numer zamowienia: ").append(id).append("\n");
            sb.append("Nazwa klienta: ").append(customer.name()).append(" -> ").append(customer.loyaltyLevel());
            sb.append("\n");

            for(int i = 0; i < items.length; i++)
            {
                sb.append(items[i].formatted()).append("\n");
            }

            sb.append("\n");
            sb.append(temp2).append("\n");

            double subtotal = calculateSubtotal();
            sb.append(formatLine("Suma: ", subtotal)).append("\n");

            if(discount != null)
            {
                double savings = discount.savings(subtotal);
                sb.append(formatNegLine("Rabat: " + discount.getDescription(), savings)).append("\n");
            }

            sb.append(temp2).append("\n");
            sb.append(formatLine("DO ZAPLATY: ", calculateTotal())).append("\n");

            sb.append(temp1).append("\n");
            sb.append(center("DZIEKUJEMY ZA WIZYTE!")).append("\n");
            sb.append(temp1).append("\n");

            return sb.toString();
        }
    }

    public static class Builder
    {
        private final int INITIAL_CAPACITY = 4;
        private final Customer customer;
        private final int id;
        private OrderItem[] items;
        private int size;
        private Discount discount;

        public Builder(int id, Customer customer)
        {
            if(id <= 0)
            {
                throw new IllegalArgumentException("ID zamowienia musi byc wieksze od 0");
            }
            if(customer == null)
            {
                throw new IllegalArgumentException("Klient nie moze byc rowny null");
            }

            this.id = id;
            this.customer = customer;

            OrderItem[] temp = new OrderItem[INITIAL_CAPACITY];
            this.items = temp;

            this.size = 0;
        }

        private void grow()
        {
            OrderItem[] temp = new OrderItem[items.length * 2];

            for(int i = 0; i < size; i++)
            {
                temp[i] = items[i];
            }

            this.items = temp;
        }

        public Builder addItem(Product product, int quantity)
        {
            if(size == items.length)
            {
                grow();
            }

            items[size] = new OrderItem(product, quantity);
            size++;

            return this;
        }

        public Builder addItem(Product product)
        {
            return addItem(product, 1);
        }

        public Builder withDiscount(Discount discount)
        {
            this.discount = discount;

            return this;
        }

        public Order build()
        {
            if(size <= 0)
            {
                throw new IllegalStateException("Zamowienie musi zawierac co najmniej jedna pozycje");
            }

            OrderItem[] temp = new OrderItem[size];

            for(int i = 0; i < size; i++)
            {
                temp[i] = items[i];
            }

            return new Order(id, temp, customer, discount);
        }
    }
}
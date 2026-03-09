public class Cafe 
{
    private final String name;
    private Product[] menu;
    private int menuSize;
    private Order[] orders;
    private int orderCount;

    public Cafe(String name, int menuCapacity, int orderCapacity)
    {
        if(name == null | name.isBlank())
        {
            throw new IllegalArgumentException("Nazwa kawiarni nie moze byc pusta");
        }
        if(menuCapacity <= 0 | orderCapacity <= 0)
        {
            throw new IllegalArgumentException("Menu oraz zamowienie musi byc wieksze od zera");
        }

        this.name = name;

        Product[] products = new Product[menuCapacity];
        this.menu = products;
        menuSize = 0;

        Order[] order = new Order[orderCapacity];
        this.orders = order;
        orderCount = 0;
    }

    public void addProduct(Product product)
    {
        if(product == null)
        {
            throw new IllegalArgumentException("Produkt nie moze byc rowny null");
        }

        if(menuSize == menu.length)
        {
            Product[] bigger = new Product[menu.length * 2];

            for(int i = 0; i < menu.length; i++)
            {
                bigger[i] = menu[i];
            }

            this.menu = bigger;
        }

        menu[menuSize] = product;
        menuSize++;
    }

    public boolean removeProduct(String productName)
    {
        int index = -1;

        for(int i = 0; i < menuSize; i++)
        {
            if(menu[i].name().equalsIgnoreCase(productName))
            {
                index = i;
                break;
            }
        }

        if(index == -1)
        {
            return false;
        }
        
        for(int i = index; i < menuSize - 1; i++)
        {
            menu[i] = menu[i + 1];
        }

        menu[menuSize - 1] = null;
        menuSize--;

        return true;
    }

    public Product[] getProductsByCategory(String category)
    {
        int count = 0;

        for(int i = 0; i < menu.length; i++)
        {
            if(menu[i].category().equalsIgnoreCase(category))
            {
                count++;
            }
        }

        Product[] output = new Product[count];

        int idx = 0;

        for(int i = 0; i < menu.length; i++)
        {
            if(menu[i].category().equalsIgnoreCase(category))
            {
                output[idx] = menu[i];
                idx++;
            }
        }

        return output;
    }

    public void sortMenuByPrice()
    {
        for(int i = 1; i < menuSize; i++)
        {
            Product key =  menu[i];

            int j = i - 1;

            while(j >= 0 && menu[j].price() > key.price())
            {
                menu[j + 1] = menu[j];
                j--;
            }

            menu[j + 1] = key;
        }
    }

    public void displayMenu()
    {
        IO.println("=== MENU: " + name.toUpperCase() + " ===");

        for(int i = 0; i < menuSize; i++)
        {
            IO.println(i + " " + menu[i].formatted());
        }

        IO.println();
    }

    public void addOrder(Order order)
    {
        if(order == null)
        {
            throw new IllegalArgumentException("Zamowienie nie moze byc rowne null");
        }

        if(orderCount == orders.length)
        {
            Order[] bigger = new Order[orders.length * 2];

            for(int i = 0; i < orders.length; i++)
            {
                bigger[i] = orders[i];
            }

            this.orders = bigger;
        }

        orders[orderCount] = order;
        orderCount++;
    }

    public Order[] getOrdersByCustomer(String customerName)
    {
        int count = 0;

        for(int i = 0; i < orderCount; i++)
        {
            if(orders[i].getCustomer().name().equalsIgnoreCase(customerName))
            {
                count++;
            }
        }

        Order[] output = new Order[count];

        int idx = 0;

        for(int i = 0; i < orderCount; i++)
        {
            if(orders[i].getCustomer().name().equalsIgnoreCase(customerName))
            {
                output[idx] = orders[i];
                idx++;
            }
        }

        return output;
    }

    public void sortOrdersByTotal()
    {
        for(int i = 0; i < orderCount - 1; i++)
        {
            for(int j = 0; j < orderCount - 1 - i; j++)
            {
                if(orders[j].calculateTotal() > orders[j + 1].calculateTotal())
                {
                    Order temp = orders[j];
                    orders[j] = orders[j + 1];
                    orders[j + 1] = temp;
                }
            }
        }
    }

    public String getName()
    {
        return name;
    }

    public int getMenuSize()
    {
        return menuSize;
    }

    public int getOrderCount()
    {
        return orderCount;
    }

    public Order[] getOrders()
    {
        Order[] output = new Order[orderCount];

        for(int i = 0; i < orderCount; i++)
        {
            output[i] = orders[i];
        }

        return output;
    }

    public static class Statistics
    {
        private final Order[] orders;
        private final int count;

        public Statistics(Order[] orders, int count)
        {
            if(orders == null | count <= 0)
            {
                throw new IllegalArgumentException("Brak zamowien do analizy");
            }

            this.orders = orders;
            this.count = count;
        }

        public double totalRevenue()
        {
            double sum = 0;

            for(int i = 0; i < count; i++)
            {
                sum += orders[i].calculateTotal();
            }

            return sum;
        }

        public double averageOrderValue()
        {
            return totalRevenue() / count;
        }

        public Order mostExpensiveOrder()
        {
            Order max = orders[0];

            for(int i = 1; i < count - 1; i++)
            {
                if(max.calculateTotal() < orders[i].calculateTotal())
                {
                    max = orders[i];
                }
            }

            return max;
        }

        public Order cheapestOrder()
        {
            Order min = orders[0];

            for(int i = 1; i < count - 1; i++)
            {
                if(min.calculateTotal() > orders[i].calculateTotal())
                {
                    min = orders[i];
                }
            }

            return min;
        }

        public int totalItemsSold()
        {
            int total = 0;

            for(int i = 0; i < count; i++)
            {
                total += orders[i].getItemCount();
            }

            return total;
        }

        public String summary()
        {
            StringBuilder sb = new StringBuilder();

            sb.append("STATYSTYKI").append("\n");
            sb.append(String.format("Liczba zamowien: %d%n", count));
            sb.append(String.format("Liczba sprzedanych sztuk: %d%n", totalItemsSold()));
            sb.append(String.format("Laczny przychod: %.2f zl%n", totalRevenue()));
            sb.append(String.format("Srednia wartosc zamowienia: %.2f zl%n", averageOrderValue()));
            sb.append(String.format("Najdrozsze zamowienie: #%d (%.2f zl)%n", mostExpensiveOrder().getId(), mostExpensiveOrder().calculateTotal()));
            sb.append(String.format("Najtansze zamowienie: #%d (%.2f zl)%n", cheapestOrder().getId(), cheapestOrder().calculateTotal()));

            return sb.toString();
        }
    }

    public class DailyReport
    {
        private final String reportDate;

        public DailyReport(String reportDate)
        {
            if(reportDate == null | reportDate.isBlank())
            {
                throw new IllegalArgumentException("Data raportu nie moze byc pusta");
            }

            this.reportDate = reportDate;
        }

        public String generate()
        {
            StringBuilder sb = new StringBuilder();
            String separator = "=".repeat(50);

            sb.append(separator).append("\n");
            sb.append(String.format("RAPORT DZIENNY - %s%n", name));
            sb.append(String.format("Data: %s%n", reportDate));
            sb.append(separator).append("\n");
            sb.append("\n");

            sb.append(String.format("Liczba produktow w menu: %d%n", menuSize));
            sb.append(String.format("Liczba zamowien: %d%n", orderCount));
            sb.append("\n");

            if(orderCount > 0)
            {
                sb.append("Lista zamowien:\n");

                for(int i = 0; i < orderCount; i++)
                {
                    sb.append(String.format("#%d | Klient: %s | Suma: %.2f zl | Sztuk: %d%n", 
                        orders[i].getId(),
                        orders[i].getCustomer().name(),
                        orders[i].calculateTotal(),
                        orders[i].getItemCount()
                    ));
                }

                sb.append("\n");

                Statistics stats = new Statistics(orders, orderCount);
                sb.append(String.format("Laczny przychod: %.2f zl%n", stats.totalRevenue()));
                sb.append(String.format("Srednia wartosc zamowienia: %.2f zl%n", stats.averageOrderValue()));
            }
            else
            {
                sb.append("Brak zamowien w danym dniu.\n");
            }

            sb.append("\n");
            sb.append(separator).append("\n");

            return sb.toString();
        }
    }
}
public record OrderItem(Product product, int quantity)
{
    public OrderItem
    {
        if(product == null)
        {
            throw new IllegalArgumentException("Produkt nie moze byc pusty");
        }
        if(quantity < 1)
        {
            throw new IllegalArgumentException("Ilosc musi wynosic co najmniej 1. Otrzymano: " + quantity);
        }
    }

    public double totalPrice()
    {
        double cena = product.price();
        cena *= quantity;

        return cena;
    }

    public String formatted()
    {
        final int ROW_WIDTH = 40;

        String left = String.format("Ilosc produktu: %d -> %s", quantity, product.name());
        String right = String.format("%.2f zl", totalPrice());

        int spacesCount = ROW_WIDTH - left.length() - right.length();

        if(spacesCount < 1)
        {
            spacesCount = 1;
        }

        return left + " ".repeat(spacesCount) + right;
    }
}
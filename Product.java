public record Product(String name, double price, String category)
{
    public Product
    {
        if(name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Nazwa produktu nie moze byc pusta.");
        }
        if(price < 0)
        {
            throw new IllegalArgumentException("Cena musi być większa od 0. Otrzymano: " + price);
        }
        if(category == null || category.isBlank())
        {
            throw new IllegalArgumentException("Kategoria produktu nie moze byc pusta");
        }
    }

    public String formatted()
    {
        return String.format("%s (%s) - %.2f zl", name, category, price);
    }
}

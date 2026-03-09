public abstract class Discount 
{
    private final String description;

    public Discount(String description)
    {
        if(description == null || description.isBlank())
        {
            throw new IllegalArgumentException("Opis rabatu nie moze byc pusty");
        }

        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public abstract double apply(double originalPrice);

    public double savings(double originalPrice)
    {
        double cena_po = apply(originalPrice);
        double result = originalPrice - cena_po;

        return result;
    }

    @Override
    public String toString()
    {
        return "Rabat:  " + description;
    }
}
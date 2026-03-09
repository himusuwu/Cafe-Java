public class PercentageDiscount extends Discount
{
    private final double percentage;

    public PercentageDiscount(double percentage)
    {
        super(String.format("Procentowa znizka %.2f", percentage));

        if(percentage < 0 | percentage > 100)
        {
            throw new IllegalArgumentException("Procent rabatu musi być w zakresie (0, 100]. Otrzymano: " + percentage);
        }

        this.percentage = percentage;
    }

    public double getPercentage()
    {
        return percentage;
    }

    @Override
    public double apply(double originalPrice)
    {
        double discounted = originalPrice * (1 - percentage / 100);

        return Math.max(discounted, 0);
    }
}
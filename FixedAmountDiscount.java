public class FixedAmountDiscount extends Discount
{
    private final double amount;

    public FixedAmountDiscount(double amount)
    {
        super(String.format("Rabat kwotowy %.2f", amount));

        if(amount <= 0)
        {
            throw new IllegalArgumentException("Kwota rabatu nie moze byc ujemna -> " + amount);
        }

        this.amount = amount;
    }

    public double getAmount()
    {
        return amount;
    }

    @Override
    public double apply(double originalPrice)
    {
        double result = originalPrice - amount;

        return Math.max(result, 0);
    }
}
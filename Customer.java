public record Customer(String name, String email, int loyaltyPoints)
{
    public Customer
    {
        if(name == null || name.isBlank())
        {
            throw new IllegalArgumentException("Imie klienta nie moze byc puste.");
        }
        if(email == null || !email.contains("@"))
        {
            throw new IllegalArgumentException("Email nie moze byc pusty oraz musi byc poprawny");
        }
        if(loyaltyPoints < 0)
        {
            throw new IllegalArgumentException("Liczba punktow lojalnosciowych nie moze byc mniejsza od 0");
        }
    }

    public String loyaltyLevel()
    {
        if(loyaltyPoints > 200)
        {
            return "ZLOTY";
        }
        else if(loyaltyPoints >= 100)
        {
            return "SREBRNY";
        }
        else if(loyaltyPoints >= 50)
        {
            return "BRAZOWY";
        }
        else
        {
            return "STANDARD";
        }
    }

    public String formatted()
    {
        return String.format("%s - %s -> %d", name, loyaltyLevel(), loyaltyPoints);
    }
}
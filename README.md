# System kawiarni — projekt (Java)

## O projekcie
Ten projekt został przygotowany jako **zadanie ze studiów** z przedmiotu GUI w języku Java.

Aplikacja przedstawia prosty system obsługi kawiarni: menu produktów, klientów, zamówienia, rabaty oraz podstawowe raportowanie.

> To jest **moje przykładowe rozwiązanie** zadania — nie jest to jedyna możliwa implementacja.

## Zakres funkcjonalności
- zarządzanie menu kawiarni (`Cafe`):
  - dodawanie i usuwanie produktów,
  - filtrowanie produktów po kategorii,
  - sortowanie menu po cenie,
  - wyświetlanie menu;
- model danych oparty o rekordy:
  - `Product` (nazwa, cena, kategoria),
  - `Customer` (imię, email, punkty lojalnościowe i poziom klienta),
  - `OrderItem` (produkt + ilość);

- obsługa zamówień (`Order` + `Order.Builder`):
  - budowanie zamówienia krok po kroku,
  - liczenie liczby pozycji, liczby sztuk, sumy i kwoty końcowej,
  - możliwość zmiany rabatu po utworzeniu zamówienia;
- system rabatów:
  - klasa abstrakcyjna `Discount`,
  - rabat procentowy `PercentageDiscount`,
  - rabat kwotowy `FixedAmountDiscount`,
  - przykład klasy anonimowej dla rabatu lojalnościowego;
- wydruk paragonu (`Order.Receipt`):
  - formatowana lista pozycji,
  - podsumowanie z uwzględnieniem rabatu,
  - data/czas, numer zamówienia i dane klienta;
- raporty i statystyki:
  - sortowanie zamówień po wartości,
  - ranking wg liczby sztuk,
  - filtrowanie zamówień po kliencie,
  - podsumowania dzienne/statystyczne (`Cafe.Statistics`, `Cafe.DailyReport`);
- walidacja danych wejściowych i demonstracja wyjątków w `Demo`.

## Uruchomienie
W katalogu projektu:

```bash
javac -d build Demo.java
java -cp build Demo
```

## Uwagi
- Projekt ma charakter edukacyjny i demonstracyjny.
- Kod skupia się na ćwiczeniu podstaw OOP, pracy na tablicach, walidacji danych, klasach zagnieżdżonych i wzorcu Builder.

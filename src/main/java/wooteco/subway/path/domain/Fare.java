package wooteco.subway.path.domain;

public class Fare {
    private static final int BASIC_FARE = 1250;
    private static final int OVER_FARE = 2050;
    private static final double FARE_PER_KM = 100;
    private static final int FIRST_OVER_FARE_DISTANCE = 10;
    private static final int SECOND_OVER_FARE_DISTANCE = 50;

    private double fare;

    public Fare(int distance, double extraLineFare, int age) {
        this.fare = calculateFare(distance, extraLineFare, age);
    }

    public double calculateFare(int distance, double extraFare, int age) {
        double baseFare = BASIC_FARE + extraFare;

        if (distance <= FIRST_OVER_FARE_DISTANCE) {
            return calculateAgeDiscount(baseFare, age);
        }

        if (distance <= SECOND_OVER_FARE_DISTANCE) {
            double fare = calculateOverFare(baseFare, distance - FIRST_OVER_FARE_DISTANCE, 5);
            return calculateAgeDiscount(fare, age);
        }

        baseFare = OVER_FARE + extraFare;
        double fare = calculateOverFare(baseFare, distance - SECOND_OVER_FARE_DISTANCE, 8);
        return calculateAgeDiscount(fare, age);
    }

    private double calculateOverFare(double baseFare, int distance, int perDistance) {
        return baseFare + (Math.ceil(distance / perDistance) * FARE_PER_KM);
    }

    private double calculateAgeDiscount(double fare, int age) {
        return fare - ((fare - 350) * discountValue(age));
    }

    public double discountValue(int age) {
        if (age >= 6 && age < 13) {
            return 0.5;
        }
        if (age >= 13 && age < 19) {
            return 0.2;
        }
        return 0;
    }

    public double fare() {
        return fare;
    }
}

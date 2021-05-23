package wooteco.subway.path.domain;

public class Fare {
    private static final int BASIC_FARE = 1250;
    private static final int OVER_FARE = 2050;
    private static final double FARE_PER_KM = 100;
    private static final int FIRST_OVER_FARE_DISTANCE = 10;
    private static final int SECOND_OVER_FARE_DISTANCE = 50;
    private static final int REQUIRED_MIN_FARE = 350;
    private static final int MIN_KID_AGE = 6;
    private static final int MIN_TEENAGER_AGE = 13;
    private static final int MIN_ADULT_AGE = 19;
    private static final double KID_DISCOUNT_RATE = 0.5;
    private static final double TEENAGER_DISCOUNT_RATE = 0.2;
    private static final double ADULT_DISCOUNT_RATE = 0;

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
        return fare - ((fare - REQUIRED_MIN_FARE) * discountValue(age));
    }

    public double discountValue(int age) {
        if (age >= MIN_KID_AGE && age < MIN_TEENAGER_AGE) {
            return KID_DISCOUNT_RATE;
        }
        if (age >= MIN_TEENAGER_AGE && age < MIN_ADULT_AGE) {
            return TEENAGER_DISCOUNT_RATE;
        }
        return ADULT_DISCOUNT_RATE;
    }

    public double fare() {
        return fare;
    }
}

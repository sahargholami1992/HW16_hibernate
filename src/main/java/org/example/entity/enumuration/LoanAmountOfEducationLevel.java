package org.example.entity.enumuration;


public enum LoanAmountOfEducationLevel {
    ASSOCIATE(1900000,1300000),
    BACHELOR(1900000,1300000),
    BACHELOR_DISCONTINUOUS(1900000,1300000),
    MASTER(2600000,2250000),
    MASTER_DISCONTINUOUS(2600000,2250000),
    DOCTORATE(2600000,2250000),
    PHD(65000000,2260000);

    private double tuition;
    private double educational;
    LoanAmountOfEducationLevel(double tuition, double educational) {
        this.tuition=tuition;
        this.educational=educational;
    }
    public double getTuition() {
        return tuition;
    }
    public double getEducational() {
        return educational;
    }

}

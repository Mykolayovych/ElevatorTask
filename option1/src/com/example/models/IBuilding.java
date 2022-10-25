package com.example.models;

public interface IBuilding {
    void startElevator(int stepsView);
    int addPassengersToElevator();
    int removePassengersFromElevator();
    boolean getDirectionMajorityPeople();
    void showInformationElevator(int step, int removedPassengers, int addedPassengers);
}

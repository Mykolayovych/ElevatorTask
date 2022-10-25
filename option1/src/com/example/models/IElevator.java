package com.example.models;

public interface IElevator {
    void move();
    boolean isEmpty();
    boolean isFull();
    boolean isDirection();
    void correctDirection();
    void addPassenger(int passengerFloor);
    int removePassengers();
    int findClosestPassengerFloorIfElevatorFull();
    void setDirection(boolean direction);
    int getCurrentFloor();
}

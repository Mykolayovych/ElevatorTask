package com.example.models.impl;

import com.example.models.IElevator;
import static com.example.consts.Const.MAX_PASSENGERS;

public class Elevator implements IElevator {
    private final int[] passengers = new int[MAX_PASSENGERS];
    private int currentFloor = 1;
    private final int maxFloor;
    private boolean direction = true;

    public Elevator(int maxFloor){
        this.maxFloor = maxFloor;
    }

    @Override
    public void move(){
        this.correctDirection();
        int nextFloor;

        if(this.isFull()) {
            nextFloor = direction ? currentFloor + 1: currentFloor - 1;
        }
        else nextFloor = findClosestPassengerFloorIfElevatorFull();
        currentFloor=nextFloor;
    }

    @Override
    public boolean isEmpty(){

        for(int i : passengers)
            if(i != 0) return false;

        return true;
    }

    @Override
    public boolean isFull(){
        boolean isElevatorFull= true;

        for(int i = 0; i < MAX_PASSENGERS; i++)
            if(passengers[i] == 0) {
                isElevatorFull = false;
                break;
            }

        return !isElevatorFull;
    }

    @Override
    public boolean isDirection() {
        return direction;
    }

    @Override
    public void correctDirection(){

        if(currentFloor == 1) direction = true;
        else if(currentFloor == maxFloor) direction = false;
    }

    @Override
    public void addPassenger(int passengerFloor){

        for(int i = 0; i < MAX_PASSENGERS; i++)

            if(passengers[i] == 0){
                passengers[i] = passengerFloor;
                return;
            }
    }

    @Override
    public int removePassengers(){
        int removedPassengersCount = 0;

        for(int i = 0; i < MAX_PASSENGERS; i++)
           if(passengers[i] == currentFloor){
            passengers[i] = 0;
            removedPassengersCount++;
           }

        return removedPassengersCount;
    }

    @Override
    public int findClosestPassengerFloorIfElevatorFull(){
        int result;

        if(direction){
            int min = maxFloor + 1;

            for(int i : passengers)
                if(i != 0 && i < min ) min = i;
            result = (min != maxFloor + 1) ? min : 0;
        }else {
            int max = 0;

            for(int i : passengers)
                if(i > max) max = i;
            result = max;
        }

        if(result == 0) throw new IllegalStateException("Method can`t find next floor!");

        return result;
    }

    @Override
    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();

        for (int passenger : passengers) {
            if (passenger != 0) res.append(passenger).append(" ");
        }

        if(res.length() > 0) res.deleteCharAt(res.length() - 1);

        return res.toString();
    }
}

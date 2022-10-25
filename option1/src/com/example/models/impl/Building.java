package com.example.models.impl;

import com.example.models.IBuilding;
import com.example.models.IElevator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.example.consts.Const.PASS_FlOOR;

public class Building implements IBuilding {
    private final int floors;
    private final IElevator elevator;
    private final List<Integer>[] passengersInFloor;
    private static final Random rnd = new Random();

    public Building(int floors){
        this.floors = floors;
        elevator = new Elevator(floors);
        passengersInFloor = new List[floors];
        fillRandomPassengers();
    }

    public void startElevator(int stepsView){

        for(int i = 1; i <= stepsView; i++){
            int removedPassengers = this.removePassengersFromElevator();

            if(elevator.isEmpty()) elevator.setDirection(this.getDirectionMajorityPeople());

            int addedPassengers = this.addPassengersToElevator();

            if(removedPassengers == 0 && addedPassengers == 0) i--;
            else {
                createRandomPassengers(removedPassengers);
                this.showInformationElevator(i, removedPassengers, addedPassengers);
            }
            elevator.move();
        }
    }


    public int addPassengersToElevator(){
        elevator.correctDirection();

        List<Integer> indexesToDelete = new ArrayList<>();
        for(int i = 0; i < passengersInFloor[elevator.getCurrentFloor() - 1].size() && elevator.isFull(); i++){

            if(elevator.isDirection()){
                if(passengersInFloor[elevator.getCurrentFloor() - 1].get(i) > elevator.getCurrentFloor()){
                   indexesToDelete.add(i);
                   elevator.addPassenger(passengersInFloor[elevator.getCurrentFloor() - 1].get(i));
                }
            } else{
                if(passengersInFloor[elevator.getCurrentFloor() - 1].get(i) < elevator.getCurrentFloor()){
                    indexesToDelete.add(i);
                    elevator.addPassenger(passengersInFloor[elevator.getCurrentFloor() - 1].get(i));
                }
            }
        }

        if (indexesToDelete.size() > 0) {
            passengersInFloor[elevator.getCurrentFloor() - 1].subList(0, indexesToDelete.size()).clear();
        }

        return indexesToDelete.size();
    }

    public int removePassengersFromElevator(){
        return elevator.removePassengers();
    }

    private void fillRandomPassengers(){

        for(int i = 0;i < floors; i++){
            passengersInFloor[i]= fillFloor(i + 1);
        }
    }

    private List<Integer> fillFloor(int currentFloor){
        List<Integer> floor = new ArrayList<>();
        int passInTheFloor = rnd.nextInt(PASS_FlOOR + 1);

        for(int j = 1; j<passInTheFloor; j++) {
            floor.add(createRandomPassenger(currentFloor));
        }
        return floor;
    }

    private int createRandomPassenger(int currentFloor){
        int passengerTargetFloor = currentFloor;

        while(passengerTargetFloor == currentFloor)
            passengerTargetFloor = rnd.nextInt(floors) + 1;

        return passengerTargetFloor;
    }

    private void createRandomPassengers(int count){

        for(int j = 0; j < count; j++)
            this.passengersInFloor[elevator.getCurrentFloor() - 1].add(
                    createRandomPassenger(elevator.getCurrentFloor()));
    }

    public boolean getDirectionMajorityPeople(){

        if(elevator.getCurrentFloor() == 1) return true;
        else if(elevator.getCurrentFloor() == floors) return false;
        else {
            int peoplesWhowantUp=0;

            for(int i = 0; i < passengersInFloor[elevator.getCurrentFloor() - 1].size(); i++)
                if(passengersInFloor[elevator.getCurrentFloor() - 1].get(i) > elevator.getCurrentFloor())
                    peoplesWhowantUp++;

            return passengersInFloor[elevator.getCurrentFloor() - 1].size() - peoplesWhowantUp < peoplesWhowantUp;
        }
    }

    public void showInformationElevator(int step, int removedPassengers, int addedPassengers){
        System.out.println("Step " + step);
        System.out.print(this);
        System.out.println("Leave: "+ removedPassengers + " Entry: " + addedPassengers + "\n");
    }

    public String toString(){
        StringBuilder result = new StringBuilder();

        for(int i = floors - 1; i >= 0; i--){

            if(elevator.getCurrentFloor() != i + 1)
                result.append(i + 1).append(" floor: ").append(passengersInFloor[i].toString()).append("\n");
            else
                result.append(i + 1).append(" floor: ").append(passengersInFloor[i].toString()).append(" Elevator:{").append(elevator).append("}\n");
        }
        return result.toString();
    }
}

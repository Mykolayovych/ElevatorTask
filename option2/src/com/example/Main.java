package com.example;

import com.example.models.Building;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random rnd = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input Steps of View:");
        int a = sc.nextInt();
        Building building = new Building((rnd.nextInt(16)+5));
        System.out.println(building);
        building.startElevator(a);
    }
}

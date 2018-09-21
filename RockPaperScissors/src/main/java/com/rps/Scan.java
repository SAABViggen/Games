package com.rps;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Scan {

    public final static Set<String> KEYS_MENU =  new HashSet<>(Arrays.asList("g", "s", "x"));
    public final static Set<String> KEYS_GAME = new HashSet<>(Arrays.asList("1", "2", "3", "x"));
    public final static Set<String> KEYS_GAME_MODE = new HashSet<>(Arrays.asList("1", "2", "3"));
    public final static Set<String> KEYS_EXIT = new HashSet<>(Arrays.asList("y", "n"));

    private Scanner scanner = new Scanner(System.in);

    public String scanName() {
        return scanner.nextLine();
    }

    public String scan(Set<String> keys) {
        String s = scanner.nextLine();
        while (!keys.contains(s)) {
            System.out.println("Wrong button, please try again. ");
            s = scanner.nextLine();
        }
        return s;
    }
}
package CoffeeMachine;

import java.util.Scanner;

enum State {
    BUY, FILL, TAKE, REMAIN, EXIT
}
public class CoffeeMachine {
    static boolean run = true;
    public static void main(String[] args) {
           while (run) {
               CoffeeProcessor.control();           
           }
    }
}

class CoffeeProcessor {
    
    static int supWaterMl = 400;
    static int supMilkMl = 540;
    static int supBeansG = 120;
    static int supCups = 9;
    static int money = 550;
        
    //resource requirements
    final static int ESPRESSO_WATER = 250;
    final static int ESPRESSO_BEANS = 16;
    final static int ESPRESSO_PRICE = 4;
                    
    final static int LATTE_WATER = 350;
    final static int LATTE_MILK = 75;
    final static int LATTE_BEANS = 20;
    final static int LATTE_PRICE = 7;
                    
    final static int CAPPUCCINO_WATER = 200;
    final static int CAPPUCCINO_MILK = 100;
    final static int CAPPUCCINO_BEANS = 12;
    final static int CAPPUCCINO_PRICE = 6;
    
    final static Scanner scanner = new Scanner(System.in);     

    public static void control() {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        String input = scanner.next();
        State state = null;
        switch (input) {
            case "buy":
                state = State.BUY;
                break;
            case "fill":
                state = State.FILL;
                break;
            case "take":
                state = State.TAKE;
                break;
            case "remaining":
                state = State.REMAIN;
                break;
            case "exit":
                state = State.EXIT;
                break;
            default:
                break;
        }
        perform(state);
    }
    
    public static void perform(State action) {
        switch (action) {
            case BUY:
                makeCoffee();
                break;
            case TAKE: 
                takeMoney();
                break;
            case FILL:
                fill();
                break;
            case REMAIN:
                showResources();
                break;
            case EXIT:
                CoffeeMachine.run = false;
                break;
        }
    }
    
    public static void showResources() {
        System.out.println("The coffee machine has:");
        System.out.println(supWaterMl + " ml of water");
        System.out.println(supMilkMl + " ml of milk");
        System.out.println(supBeansG + " g of coffee beans");
        System.out.println(supCups + " disposable cups");
        System.out.println("$" + money + " of money");
    }
    
    public static void fill() {
        System.out.println("Write how many ml of water you want to add:");
        int addWater = scanner.nextInt();
        supWaterMl += addWater;
                    
        System.out.println("Write how many ml of milk you want to add");
        int addMilk = scanner.nextInt();
        supMilkMl += addMilk; 
                    
        System.out.println("Write how many grams of coffee beans you want to add:");
        int addBeans = scanner.nextInt();
        supBeansG += addBeans;
                    
        System.out.println("Write how many disposable cups of coffee you want to add:");
        int addCups = scanner.nextInt();
        supCups += addCups;
    }
    
    public static void takeMoney() {
        System.out.println("I gave you " + money + '$');
        money = 0;
    }
    
    public static void printProblem(String problem) {
        switch (problem) {
            case "water":
                System.out.println("Sorry, not enough water!");
                break;
            case "milk":
                System.out.println("Sorry, not enough milk!");
                break;
            case "beans":
                System.out.println("Sorry, not enough coffee beans!");
                break;
            case "cups":
                System.out.println("Sorry, not enough disposable cups!");
                break;
            default: 
                break;
        }
    }
    
    public static void makeCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String option = scanner.next();
        
        switch (option) {
            case "1":
                if (supWaterMl - ESPRESSO_WATER <= 0) {
                    printProblem("water");
                } else if (supBeansG - ESPRESSO_BEANS <= 0) {
                    printProblem("beans");
                } else if (supCups - 1 <= 0) {
                   printProblem("cups");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    supWaterMl -= ESPRESSO_WATER;
                    supBeansG -= ESPRESSO_BEANS;
                    supCups--;
                    money += ESPRESSO_PRICE;
                }
                break;
            case "2":
                if (supWaterMl - LATTE_WATER <= 0) {
                    printProblem("water");
                } else if (supMilkMl - LATTE_MILK <= 0) {
                    printProblem("milk");
                } else if (supBeansG - LATTE_BEANS <= 0) {
                    printProblem("beans");
                } else if (supCups - 1 <= 0) {
                   printProblem("cups");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    supWaterMl -= LATTE_WATER;
                    supMilkMl -= LATTE_MILK;
                    supBeansG -= LATTE_BEANS;
                    supCups--;
                    money += LATTE_PRICE;
                }
                break;
            case "3":
                if (supWaterMl - CAPPUCCINO_WATER <= 0) {
                    printProblem("water");
                } else if (supMilkMl - CAPPUCCINO_MILK <= 0) {
                    printProblem("milk");
                } else if (supBeansG - CAPPUCCINO_BEANS <= 0) {
                    printProblem("beans");
                } else if (supCups - 1 <= 0) {
                   printProblem("cups");
                } else {
                    System.out.println("I have enough resources, making you a coffee!");
                    supWaterMl -= CAPPUCCINO_WATER;
                    supMilkMl -= CAPPUCCINO_MILK;
                    supBeansG -= CAPPUCCINO_BEANS;
                    supCups--;
                    money += CAPPUCCINO_PRICE;
                }
                break;
            case "back":
                break;
        }
    }
}

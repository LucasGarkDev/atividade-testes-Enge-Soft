package com.devemg;

public class Main {
    private static final int LAST_OPTION_MENU = 7;
    public static void main(String[] args) {
        HandlerProducts handlerProducts = new HandlerProducts();
        int option;
        do {
            option = handlerProducts.showMenu();
            switch (option) {
                case 1 -> // create product
                        handlerProducts.createProduct();
                case 2 -> //list products
                        handlerProducts.showAll();
                case 3 -> //see single product
                        handlerProducts.showOne();
                case 4 -> //update product
                        handlerProducts.update();
                case 5 -> //delete product
                        handlerProducts.delete();
                case 6 -> //config database
                        handlerProducts.updateDatabase();
                case LAST_OPTION_MENU -> //exit
                        System.out.println("bye!");
                default -> System.out.println("Option " + option + " not found");
            }
        } while (option!=LAST_OPTION_MENU);
    }


}

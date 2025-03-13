package holo;

import java.util.ArrayList;

public class Coordinator implements Runnable{
    private FrendStats stats;
    private ArrayList<Food> foodList;

    public Coordinator(FrendStats stats, ArrayList<Food> foodList) {
        this.stats = stats;
        this.foodList = foodList;
    }

    @Override
    public void run() {
        while (stats.isAlive()){
            for(int i = 0; i < foodList.size(); i++){
                if(foodList.get(i) != null){
                    CheckFoodCollision();
                    break;
                }
            }
            if(stats.getPositionX() != stats.getDestinationX() || stats.getPositionY() != stats.getDestinationY()) {

                if (stats.getPositionX() > stats.getDestinationX()){

                    if ((stats.getPositionX() - stats.getDestinationX()) < stats.getMsX()) {
                        stats.setPositionX(stats.getDestinationX());
                    } else {
                        stats.setPositionX(stats.getPositionX() - stats.getMsX());
                    }

                } else if (stats.getPositionX() < stats.getDestinationX()){

                    if ((stats.getDestinationX() - stats.getPositionX()) < stats.getMsX()) {
                        stats.setPositionX(stats.getDestinationX());
                    } else {
                        stats.setPositionX(stats.getPositionX() + stats.getMsX());
                    }

                }

                if (stats.getPositionY() > stats.getDestinationY()){

                    if ((stats.getPositionY() - stats.getDestinationY()) < stats.getMsY()) {
                        stats.setPositionY(stats.getDestinationY());
                    } else {
                        stats.setPositionY(stats.getPositionY() - stats.getMsY());
                    }

                } else if (stats.getPositionY() < stats.getDestinationY()){

                    if ((stats.getDestinationY() - stats.getPositionY()) < stats.getMsY()) {
                        stats.setPositionY(stats.getDestinationY());
                    } else {
                        stats.setPositionY(stats.getPositionY() + stats.getMsY());
                    }

                }
            }
            try {
                Thread.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void CheckFoodCollision(){
        for(int i = 0; i < foodList.size(); i++){
            int x = -10;
            int y = -10;
            try {
                x = foodList.get(i).getX();
                y = foodList.get(i).getY();
            } catch (Exception e){
                break;
            }

            int min_x = (int)stats.getPositionX();
            int max_x = min_x + 48;

            int min_y = (int)stats.getPositionY() - 16;
            int max_y = min_y + 128;

            if (x > min_x && x < max_x){
                if (y > min_y && y < max_y){
                    foodList.get(i).dispose();
                    foodList.set(i, null);
                    if (stats.getHunger() < 7){
                        stats.setHunger(stats.getHunger()+3);
                    } else {
                        stats.setHunger(10);
                    }
                    System.out.println("HAMBURGER EATEN");  //debug
                    break;
                }
            }
        }
    }
}

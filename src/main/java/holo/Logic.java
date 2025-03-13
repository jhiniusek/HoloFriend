package holo;

public class Logic implements Runnable{
    private FrendStats stats;

    public Logic(FrendStats stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        while(stats.isAlive()){
            if(stats.getState() != States.EAT){
                if (stats.getPositionX() == stats.getDestinationX() && stats.getPositionY() == stats.getDestinationY()) {
                    stats.setState(States.IDLE);
                    try {
                        Thread.sleep((int)(Math.random() * 5000 + 1000));
                        stats.chooseDestination();
                        stats.setState(States.WALK);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                stats.setState(States.IDLE);
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

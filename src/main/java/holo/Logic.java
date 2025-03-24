package holo;

public class Logic implements Runnable{
    private FrendStats stats;

    public Logic(FrendStats stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        while(stats.isAlive()){

            States state = stats.getState();

            switch (state) {
                case IDLE -> {
                    try {
                        Thread.sleep((int)(Math.random() * 5000 + 2000));
                        stats.chooseDestination();
                        if(stats.getState() != States.HOLD){
                            stats.setState(States.WALK);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                case WALK -> {

                }
                case HOLD -> {

                }
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

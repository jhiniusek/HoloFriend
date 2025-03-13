package holo;

public class Animator implements Runnable{
    private FrendStats stats;

    public Animator(FrendStats stats) {
        this.stats = stats;
    }

    @Override
    public void run() {
        int x = 1;
        while(stats.isAlive()){
            stats.setAnimationFrame(x);
            if(x<8){
                x++;
            }else{
                x=1;
            }
            try {
                Thread.sleep(125); //8fps
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

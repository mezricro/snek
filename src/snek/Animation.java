package snek;

public abstract class Animation {
    Thread animationThread;
    boolean run = false;

    int cycleTime;
    public Animation(int cycleTime) {
        setCycleTime(cycleTime);
    }
    
    public Animation() {
        this(300);
    }
    
    //Only start new animation if the current one isn't running
    public void start() {
        if (!run) {
            run = true;
            animationThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(run) {
                        animate();
                        try {
                            Thread.sleep(getCycleTime());
                        } catch (InterruptedException e) {

                        }
                    }
                }
            });
            animationThread.start();
        }
    }

    public int getCycleTime() {
        return cycleTime;
    }

    public void setCycleTime(int cycleTime) {
        this.cycleTime = cycleTime;
    }
    
    //Set animation steps during construction
    public abstract void animate();
    
    //Check if the animation is currently running
    public boolean isRunning() {
        return run;
    }
    
    //Terminate thread --> stop animation
    public void end() {
        run = false;
        try {
            animationThread.join();
        } catch (InterruptedException e) {
        }
    }
}

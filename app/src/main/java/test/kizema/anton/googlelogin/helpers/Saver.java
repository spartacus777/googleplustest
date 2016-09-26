package test.kizema.anton.googlelogin.helpers;

public class Saver {

    //emulates local DB

    private static Saver instance;

    private volatile int rand;

    private volatile boolean isServiceStopped = false;

    private Saver(){}

    public static synchronized Saver getInstance(){
        if (instance == null){
            instance = new Saver();
        }

        return instance;
    }

    public void set(int rand){
        this.rand = rand;
    }

    public int get(){
        return rand;
    }

    public boolean isServiceStopped() {
        return isServiceStopped;
    }

    public void setServiceStopped(boolean serviceStopped) {
        isServiceStopped = serviceStopped;
    }
}

package test.kizema.anton.googlelogin.helpers;

//emulates local DB
public class Saver {

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

    public void setRand(int rand){
        this.rand = rand;
    }

    public int getRand(){
        return rand;
    }

    public boolean isServiceStopped() {
        return isServiceStopped;
    }

    public void setServiceStopped(boolean serviceStopped) {
        isServiceStopped = serviceStopped;
    }
}

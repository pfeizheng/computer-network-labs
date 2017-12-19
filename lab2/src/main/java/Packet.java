public class Packet {
    private double arrivalTick;

    // Wait Tick includes the time waited after sensing carrier and the collision backOff time
    private double waitTick;

    private int collisionCount;

    // True if the packet is ready to be transmitted (already waited 96 tick of sense time)
    private boolean isReady;


    public Packet(double arrivalTick, double waitTick) {
        this.arrivalTick = arrivalTick;
        this.waitTick = waitTick;
        this.collisionCount = 0;
        this.isReady = false;
    }

    public double getArrivalTick() {
        return arrivalTick;
    }

    public double getWaitTick() {
        return this.waitTick;
    }

    public void incrementWaitTick(double increment) {
        this.waitTick += increment;
    }

    public int getCollisionCount() {
        return this.collisionCount;
    }

    public void incrementCollisionCount() {
        this.collisionCount++;
    }

    public boolean getIsReady() {
        return this.isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }
}

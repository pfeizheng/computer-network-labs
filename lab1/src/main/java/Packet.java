public class Packet {
    private int arrivalTick;
    private int departureTick;

    public Packet(int arrivalTick) {
        this.arrivalTick = arrivalTick;

        // negative tick means that the departure tick is not set
        this.departureTick = -1;
    }

    public int getArrivalTick() {
        return arrivalTick;
    }

    public int getDepartureTick() {
        return departureTick;
    }

    public void setDepartureTick(int tick) {
        departureTick = tick;
    }
}

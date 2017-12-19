
public class Node {
    private double nextTick;
    private int packetsPerSecond;

    public Node(int packetsPerSecond) {
        this.packetsPerSecond = packetsPerSecond;
        nextTick = 0;
    }

    public boolean isTimeToGenerate(double tick) {
        return tick >= nextTick;
    }

    //calculate the next tick to generate the packet
    public Packet generate(double tick) {
        nextTick = tick + Util.GenerateRandomVariable(packetsPerSecond) * Constants.TICKS;
        return new Packet(tick, 0);
    }
}

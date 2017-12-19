
public class PacketGenerator {
    private int nextTick;
    private int packetsPerSecond;

    public PacketGenerator(int packetsPerSecond) {
        this.packetsPerSecond = packetsPerSecond;
        nextTick = 0;
    }

    public boolean isTimeToGenerate(int tick) {
        return tick >= nextTick;
    }

    private double GenerateRandomVariable(double lambda) {
        return -Math.log(1 - Math.random()) / lambda;
    }

    public Packet generate(int tick) {
        //calculate the next tick in µs
        nextTick = tick + (int)(GenerateRandomVariable(packetsPerSecond) * 1000000.0);
        return new Packet(tick);
    }
}

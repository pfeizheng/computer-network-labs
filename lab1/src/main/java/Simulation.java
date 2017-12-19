public class Simulation {
    private final PacketGenerator generator;
    private final PacketServer server;
    private final Buffer buffer;

    // one tick represents one microseconds
    private int TICKS = 0;

    public Simulation(int lambda, double L, double C, int TICKS) {
        generator = new PacketGenerator(lambda);
        server = new PacketServer(L, C);
        buffer = new Buffer();
        this.TICKS = TICKS;
    }

    public Simulation(int lambda, double L, double C, int K, int TICKS) {
        generator = new PacketGenerator(lambda);
        server = new PacketServer(L, C);
        buffer = new Buffer(K);
        this.TICKS = TICKS;
    }

    public void start() {
        for (int i = 0; i < TICKS; i++) {
            if (generator.isTimeToGenerate(i)) {
                buffer.addPacket(generator.generate(i));
            }

            // Update total buffer size at each tick
            buffer.updateTotalBufferSize();

            // Server tries to departure at very tick
            server.departure(buffer, i);
        }
    }

    public double calAvgBufferSize() {
        return (double)buffer.getTotalBufferSize() / (double)TICKS;
    }

    public double calAvgSojournTime() {
        return (double)server.getTotalSojournTime() / (double)buffer.getTotalPackets();
    }

    public double calPercentIdle() {
        return (double)server.getIdleTime() / (double)TICKS * 100.0;
    }

    public double calPercentPacketLost() { return (double)buffer.getPacketsLost() / (double)buffer.getTotalPackets() * 100.0; }

    public void printResults() {
        System.out.println("Average number of packets:  " + calAvgBufferSize());
        System.out.println("Average sojourn time:       " + calAvgSojournTime() + " microseconds");
        System.out.println("Proportion of idle time:    " + calPercentIdle() + "%");
        System.out.println("Packet loss probability:    " + calPercentPacketLost() + "%");
    }

}


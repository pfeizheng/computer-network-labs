import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

public class Simulation {
    private final Set<Node> nodeSet = new HashSet<>();
    private final Hub hub;
    private final Bus bus;
    private final Buffer buffer;
    private final int Total_Nodes_num;

    private boolean is1Persistent;

    public Simulation(int A, double L, double W, int N, boolean is1Persistent) {
        for (int i = 0; i < N; i++) {
            nodeSet.add(new Node(A));
        }
        hub = new Hub(L, W);
        bus = new Bus();
        buffer = new Buffer();
        Total_Nodes_num = N;
        this.is1Persistent = is1Persistent;
    }

    public void start() {
        for (double tick = 0; tick < Constants.TICKS; tick++) {
            for (Node node: nodeSet) {
                if (node.isTimeToGenerate(tick)) {
                    buffer.addPacket(node.generate(tick));
                }
            }

            // Sense the medium
            sense(tick);

            // Detect the medium
            hub.detect(bus, buffer);

            // Server tries to departure at very tick
            if (!bus.isBusEmpty()) {
                hub.departure(bus, tick);
            }
        }
    }

    public void sense(double tick) {
        Iterator<Packet> packetIt = buffer.getSet().iterator();
        while(packetIt.hasNext()) {
            Packet packet = packetIt.next();
            // sense the medium, only  push the packet to the bus 96 Ticks
            // later when the bus is empty, otherwise increment the wait tick
            if (Util.checkTickEqual(packet.getArrivalTick() + packet.getWaitTick(), tick)) {

                if (!packet.getIsReady() && bus.isBusEmpty()) { // send the medium

                    packet.incrementWaitTick(Constants.SENSING_TICK
                            + Constants.TOTAL_PROP_DELAY / Total_Nodes_num);
                    packet.setIsReady(true);

                } else if (!packet.getIsReady() && !bus.isBusEmpty()) { // Wait until the bus is free

                    if (is1Persistent) packet.incrementWaitTick(1); // 1-Persistent
                    else packet.incrementWaitTick(1 +
                            Util.generateRandomWaitTime(packet.getCollisionCount())); // Non-Persistent

                } else if (packet.getIsReady()) { // Ready to transmit

                    packetIt.remove();
                    bus.addPacket(packet);

                }

            }
        }
    }

    public void printResults(int N, int A) {
        System.out.format("Average Packet Delay for N = %d, A = %d: %d%n", N, A, hub.getAvgDelay());
        System.out.format("Average throughput for N = %d, A = %d: %d%n", N, A, hub.getAvgThroughput());
    }
}


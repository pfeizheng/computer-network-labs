public class PacketServer {
    private final int serviceTime;
    private int idleTime = 0;
    private int totalSojournTime = 0;

    PacketServer(double L, double C) {
        // convert from second to µs
        serviceTime = (int)((L / C) * 1000000.0);
    }

    public int getIdleTime() {
        return idleTime;
    }

    public int getTotalSojournTime() {
        return totalSojournTime;
    }

    public void departure(Buffer buffer, int tick) {
        if(buffer.isBufferEmpty()) {
            idleTime++;
        } else {
            Packet packet = buffer.getFirstPacket();
            // Check if departure tick of first pack in the queue has been set already
            if (packet.getDepartureTick() == -1) {
                packet.setDepartureTick(tick);
            } else if (tick == packet.getDepartureTick() + serviceTime) {
                // Departures packet if service done
                int queueingDelay = packet.getDepartureTick() - packet.getArrivalTick();
                totalSojournTime += queueingDelay + serviceTime;
                buffer.removePacket();
            }
        }
    }
}

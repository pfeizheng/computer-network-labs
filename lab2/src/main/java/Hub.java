public class Hub {
    private final int serviceTime;
    private long totalDelay;
    private int totalPacketCount;

    Hub(double L, double W) {
        // convert from second to tick
        serviceTime = (int)((L / W) * Constants.TICKS);
        totalDelay = 0;
        totalPacketCount = 0;
    }

    public void departure(Bus bus, double tick) {
        Packet packet = bus.getFirstPacket();
        if (Util.checkTickEqual(packet.getArrivalTick() + packet.getWaitTick() + serviceTime, tick)) {
            bus.removePacket();
            this.totalDelay +=  packet.getWaitTick() + serviceTime;
            this.totalPacketCount++;
        }
    }

    public void detect(Bus bus, Buffer buffer) {
        if (bus.getTotalBusSize() >= 2) {
            while(!bus.isBusEmpty()) {
                Packet packet = bus.removePacket();
                packet.incrementCollisionCount();
                packet.incrementWaitTick(Util.generateRandomWaitTime(packet.getCollisionCount()) + Constants.JAMMING_TICK);
                packet.setIsReady(false);
                buffer.addPacket(packet);
            }
        }
    }

    public long getAvgDelay() {
        return this.totalDelay / this.totalPacketCount;
    }

    public int getAvgThroughput() {
        return (int)((long)this.totalPacketCount * Constants.TICKS / Constants.TICKS);
    }
}

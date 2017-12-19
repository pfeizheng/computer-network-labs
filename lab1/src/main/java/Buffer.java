import java.util.LinkedList;

public class Buffer {
    private LinkedList<Packet> queue = new LinkedList<>();
    private int totalBufferSize;
    private int totalPackets;
    private int packetsLost;
    private int bufferLimit;

    public Buffer() {
        totalBufferSize = 0;
        totalPackets = 0;
        packetsLost = 0;

        // negative buffer limit means that the buffer limit is not set
        bufferLimit = -1;
    }

    public Buffer(int K) {
        this();
        bufferLimit = K;
    }

    public int getTotalBufferSize() {
        return totalBufferSize;
    }

    public int getTotalPackets() {
        return totalPackets;
    }

    public int getPacketsLost() { return packetsLost; }

    public void updateTotalBufferSize() {
        totalBufferSize += queue.size();
    }

    public boolean isBufferEmpty() {
        return queue.isEmpty();
    }

    public Packet getFirstPacket() {
        return queue.getFirst();
    }

    public void addPacket(Packet packet) {
        if (bufferLimit != -1 && queue.size() == bufferLimit) {
            packetsLost++;
            return;
        }
        totalPackets++;
        queue.add(packet);
    }

    public void removePacket() {
        queue.remove();
    }
}

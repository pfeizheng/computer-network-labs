import java.util.LinkedList;

public class Bus {
    private LinkedList<Packet> queue = new LinkedList<>();

    public int getTotalBusSize() { return queue.size();  }

    public boolean isBusEmpty() {
        return queue.isEmpty();
    }

    public Packet getFirstPacket() {
        return queue.isEmpty() ? null: queue.getFirst();
    }

    public void addPacket(Packet packet) {
        queue.add(packet);
    }

    public Packet removePacket() {
        return queue.remove();
    }
}

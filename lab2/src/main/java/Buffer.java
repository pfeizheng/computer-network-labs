import java.util.HashSet;
        import java.util.Set;

/**
 * The buffer is used to store
 * packets waiting to be transmitted
 */

public class Buffer {
    Set<Packet> set;
    public Buffer() {
        set = new HashSet<>();
    }

    public Set<Packet> getSet() {
        return this.set;
    }

    public void addPacket(Packet packet) {
        this.set.add(packet);
    }
}

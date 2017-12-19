public class Main {
    public static void main(String[] args) {
        // Simulation for M/D/1
        int[] N = { 4,6, 8, 10, 12, 14, 16 };
        int[] A1 = { 4, 6, 8, 10, 12, 16 };
        int[] A2 = { 4, 6, 8 };

        System.out.println("*********** non-persistent CSMA/CD ******************");

        for (int n: N) {
            for (int a: A1) {
                Simulation simulation = new Simulation(a, 1000 * 8, 1000000, n, false);
                simulation.start();
                simulation.printResults(n, a);
            }
            System.out.println("****************************");
        }

        System.out.println("*********** 1-persistent CSMA/CD ******************");

        for (int n: N) {
            for (int a: A2) {
                Simulation simulation = new Simulation(a, 1000 * 8, 1000000, n, true);
                simulation.start();
                simulation.printResults(n, a);
            }
            System.out.println("****************************");
        }
    }
}

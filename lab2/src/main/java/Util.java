public class Util {
    public static double GenerateRandomVariable(double lambda) {
        return -Math.log(1 - Math.random()) / lambda;
    }

    public static int generateRandomWaitTime(int collisionCount) {
        return (int) ((Math.random() * (Math.pow(2, collisionCount) - 1)) * Constants.BACKOFF_CONST);
    }

    public static boolean checkTickEqual(double tick1, double tick2) {
        return Math.abs(tick1 - tick2) < 1;
    }
}

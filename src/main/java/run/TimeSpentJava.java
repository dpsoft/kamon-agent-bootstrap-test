package run;

public class TimeSpentJava {
    public static void main(String... args) {
        for(int i = 0; i < 10; i++) {
            new Worker().performTask();
        }
    }
}

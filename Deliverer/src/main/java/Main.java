import javax.swing.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static final int threadsCount = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadsCount);
        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < threadsCount - 1; i++) {
            futures.add(threadPool.submit(() -> {
                String route = generateRoute("RLRFR", 100);
                int rCalc = 0;
                for (int j = 0; j < route.length(); j++) {

                    if (route.charAt(j) == 'R') {
                        rCalc++;
                    }
                }
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(rCalc)) {
                        sizeToFreq.replace(rCalc, sizeToFreq.get(rCalc) + 1);
                    } else {
                        sizeToFreq.put(rCalc, 1);
                    }
                }
                System.out.println("В маршруте " + route + " найдено: " + rCalc + " R");
            }));
        }

        for (int i = 0; i < threadsCount - 1; i++) {
            futures.get(i).get();
        }
        threadPool.shutdown();

        int maxValue = 0;
        int maxKey = 0;
        System.out.println("Количество R, встречаемое в маршрутах повторялось со следующей частотой: ");
        for (Map.Entry<Integer, Integer> i : sizeToFreq.entrySet()) {
            if (i.getValue() > maxValue) {
                maxKey = i.getKey();
                maxValue = i.getValue();
            }
            System.out.println(i.getKey() + " R - " + i.getValue() + " раз");
        }
        System.out.println("Самое частое кол-во R : " + maxKey + " - " + maxValue + " раз");
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}

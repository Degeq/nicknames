import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger counterLength3 = new AtomicInteger(0);
    public static AtomicInteger counterLength4 = new AtomicInteger(0);
    public static AtomicInteger counterLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        Future task1 = threadPool.submit(() -> {
            for (String i : texts) {
                boolean flag = false;
                for (int j = 0; j < i.length() - 1; j++) {
                    if (i.charAt(j) == i.charAt(j + 1)) {
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
                switch (i.length()) {

                    case 3:
                        if (flag) {
                            counterLength3.getAndIncrement();
                        }
                        break;
                    case 4:
                        if (flag) {
                            counterLength4.getAndIncrement();
                        }
                        break;
                    case 5:
                        if (flag) {
                            counterLength5.getAndIncrement();
                        }
                        break;
                }
            }
        });

        Future task2 = threadPool.submit(() -> {
            for (String i : texts) {
                boolean flag = false;
                for (int j = 0; j < i.length(); j++) {
                    if (i.charAt(j) == i.charAt(i.length() - 1 - j)) {
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
                switch (i.length()) {

                    case 3:
                        if (flag) {
                            counterLength3.getAndIncrement();
                        }
                        break;
                    case 4:
                        if (flag) {
                            counterLength4.getAndIncrement();
                        }
                        break;
                    case 5:
                        if (flag) {
                            counterLength5.getAndIncrement();
                        }
                        break;
                }
            }
        });

        Future task3 = threadPool.submit(() -> {
            for (String i : texts) {
                boolean flag = false;
                for (int j = 0; j < i.length() - 1; j++) {
                    if (getCode("abc", i.charAt(j)) <= getCode("abc", i.charAt(j + 1))) {
                        flag = true;
                    } else {
                        flag = false;
                        break;
                    }
                }
                switch (i.length()) {

                    case 3:
                        if (flag) {
                            counterLength3.getAndIncrement();
                        }
                        break;
                    case 4:
                        if (flag) {
                            counterLength4.getAndIncrement();
                        }
                        break;
                    case 5:
                        if (flag) {
                            counterLength5.getAndIncrement();
                        }
                        break;
                }
            }
        });

        task1.get();
        task2.get();
        task3.get();

        threadPool.shutdown();

        System.out.println("Красивых слов с длиной 3: " + counterLength3);
        System.out.println("Красивых слов с длиной 4: " + counterLength4);
        System.out.println("Красивых слов с длиной 5: " + counterLength5);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static int getCode(String letters, char z) {

        for (int i = 0; i < letters.length() - 1; i++) {
            if (z == letters.charAt(i)) {
                return i;
            }
        }
        return -1;
    }
}

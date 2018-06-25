package concurrency.ch5.section7;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Search {
    static int[] arr;
    static ExecutorService executorService = Executors.newCachedThreadPool();
    static final int THREAD_NUM = 2;
    static AtomicInteger result = new AtomicInteger(-1);

    public static int search(int searchValue, int beginPos, int endPos) {
        int i = 0;
        for (i = beginPos; i < endPos; i++) {
            if (result.get() >= 0) {
                return result.get();
            }

            if (arr[i] == searchValue) {
                if (!result.compareAndSet(-1, i)) {
                    return result.get();
                }
                return i;
            }
        }
        return -1;
    }

    private static class SearchTask implements Callable<Integer> {
        int begin, end, searchValue;

        public SearchTask(int begin, int end, int searchValue) {
            this.begin = begin;
            this.end = end;
            this.searchValue = searchValue;
        }

        @Override
        public Integer call() {
            return search(searchValue, begin, end);
        }
    }

    public static int pSearch(int searchValue) throws ExecutionException, InterruptedException {
        int subArrSize = arr.length / THREAD_NUM + 1;
        ArrayList<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < arr.length; i += subArrSize) {
            int end = i + subArrSize;
            if (end >= arr.length) {
                end = arr.length;
            }
            futures.add(executorService.submit(new SearchTask(searchValue, i, end)));
        }

        for (Future<Integer> future : futures) {
            if (future.get() > 0) {
                return future.get();
            }
        }

        return -1;
    }
}

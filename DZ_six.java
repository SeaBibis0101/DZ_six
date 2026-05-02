import java.util.Arrays;
import java.util.Random;

public class SortingComparison {

    // Быстрая сортировка (Quick Sort)
    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Сортировка слиянием (Merge Sort)
    public static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Генерация случайного массива
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(10000);
        }
        return arr;
    }

    // Проверка корректности сортировки
    public static boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Размеры массивов для тестирования
        int[] sizes = {1000, 10000, 100000, 1000000};
        
        for (int size : sizes) {
            System.out.println("Тестирование для массива размером " + size + " элементов:");
            
            // Генерация массива
            int[] originalArray = generateRandomArray(size);
            int[] arrayForQuickSort = Arrays.copyOf(originalArray, originalArray.length);
            int[] arrayForMergeSort = Arrays.copyOf(originalArray, originalArray.length);

            // Тестирование быстрой сортировки
            long startTime = System.nanoTime();
            quickSort(arrayForQuickSort, 0, arrayForQuickSort.length - 1);
            long quickSortTime = System.nanoTime() - startTime;
            System.out.printf("Быстрая сортировка: %.3f мс, корректность: %b%n", 
                             quickSortTime / 1_000_000.0, isSorted(arrayForQuickSort));

            // Тестирование сортировки слиянием
            startTime = System.nanoTime();
            mergeSort(arrayForMergeSort, 0, arrayForMergeSort.length - 1);
            long mergeSortTime = System.nanoTime() - startTime;
            System.out.printf("Сортировка слиянием: %.3f мс, корректность: %b%n", 
                             mergeSortTime / 1_000_000.0, isSorted(arrayForMergeSort));

            // Сравнение производительности
            System.out.printf("Отношение времени (QuickSort/MergeSort): %.2f%n", 
                             (double)quickSortTime / mergeSortTime);
        }
    }
}

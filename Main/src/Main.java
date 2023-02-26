import java.util.Random;

public class Main{
    public static void main(String[] args){
        //test();
        benchmark1();
        //benchmark2();
    }

    public static void test(){
        ListHeap1 l1 = new ListHeap1();
        l1.remove();
        l1.add(3);
        l1.remove();
        l1.add(6);
        l1.add(2);
        l1.add(5);
        l1.add(1);
        l1.add(4);
        l1.remove();
        l1.add(7);
        l1.remove();
        System.out.println("L1 test complete");

        ListHeap2 l2 = new ListHeap2();
        l2.remove();
        l2.add(3);
        l2.remove();
        l2.add(6);
        l2.add(2);
        l2.add(5);
        l2.add(1);
        l2.add(4);
        l2.remove();
        l2.add(7);
        l2.remove();
        System.out.println("L2 test complete");

        TreeHeap tree = new TreeHeap();
        int depth;
        tree.remove();
        tree.add(3);
        tree.remove();
        tree.add(6);
        tree.add(2);
        tree.add(5);
        tree.add(1);
        tree.add(4);
        depth = tree.push(10);
        tree.remove();
        tree.add(7);
        tree.remove();
        System.out.println("Tree test complete");

        ArrayHeap arr = new ArrayHeap(100);
        for(int i = 20; i>=0; i--){
            arr.add(i);
        }
        for(int i = 20; i>=0; i--){
            arr.remove();
        }
        arr.remove();
        arr.add(3);
        arr.remove();
        arr.add(6);
        arr.add(2);
        arr.add(5);
        arr.add(1);
        arr.add(4);
        arr.remove();
        arr.add(7);
        arr.remove();
        System.out.println("Array test complete");
    }


    // Benchmark of lists versions and array heap
    public static void benchmark1(){
        int loops = 1000;
        int minLoops = 10;
        int size = 9;
        int[] sizes = createSizes(size, 10, 2);
        Heap[] heaps = {new ListHeap1(), new ListHeap2(), new ArrayHeap(sizes[sizes.length-1])};  // new ArrayHeap(1000)

        // Text displayed
        System.out.println("\nBenchmarking quicksort using array and linked list");
        System.out.println("Loops for average time: " + loops);
        System.out.println("Loops for minimum of average time: " + minLoops);
        System.out.println("Unit of time: Nanoseconds");  //(*) Unit of time
        System.out.println("\nRunning benchmark...\n");
        System.out.printf("%30s%30s%30s", "List1", "List2", "Array\n");
        System.out.printf("%s%28s%28s%28s", "Size", "add/rem", "add/rem", "add/rem\n");

        // Benchmark every size
        long t0 = System.nanoTime();  // Start measuring time to complete benchmark

        // Size
        for(int n : sizes) {

            System.out.print(n);  // Prints out the size and creates the arrays for the benchmark

            // Heap
            for(int h = 0; h < heaps.length; h++){

                // Min time
                double add_min_average = Double.MAX_VALUE;
                double remove_min_average = Double.MAX_VALUE;

                for (int minLoop = 0; minLoop < minLoops; minLoop++) {

                    // Total time
                    double add_total = 0;  // Total time to add
                    double remove_total = 0;  // Total time to remove

                    // Benchmark
                    for (int loop = 0; loop < loops; loop++) {
                        int[] values = createValues(n);
                        double[] results = heaps[h].bench(values);
                        add_total += results[0];
                        remove_total += results[1];
                    }

                    // Average
                    double add_average = add_total / loops;
                    double remove_average = remove_total / loops;

                    // Minimum
                    if (add_average < add_min_average) {
                        add_min_average = add_average;
                    }
                    if (remove_average < remove_min_average) {
                        remove_min_average = remove_average;
                    }
                }
                // Printing result
                String output = (int)add_min_average + " / " + (int)remove_min_average;
                System.out.printf("%30s", output);
            }
            System.out.println();
        }

        // Time
        long t1 = System.nanoTime();  // Stop measuring time to complete benchmark
        double time = t1-t0;
        time  = time/1000000000;
        System.out.println("\nBenchmark took " + time + " seconds");
    }


    public static void benchmark2(){

        // Init
        TreeHeap heap = new TreeHeap();
        Random rnd = new Random();
        int[] add_depths = new int[64];
        int[] push_depths = new int[add_depths.length];
        boolean[] chosen = new boolean[100];

        // Add
        for(int i = 0; i<add_depths.length; i++){  // Add
            while(true){
                int value = rnd.nextInt(100);
                if(chosen[value] == false){
                    add_depths[i] = heap.add(value);
                    chosen[value] = true;
                    break;
                }
            }
        }

        // Push
        for(int i = 0; i < add_depths.length; i++){
            push_depths[i] = heap.push(rnd.nextInt(100));
        }

        // Output
        System.out.printf("%s%20s%20s", "Nr", "Add", "Push\n");
        for(int i = 0; i< add_depths.length; i++){
            System.out.printf("%s%20s%20s", i, add_depths[i], push_depths[i]);
            System.out.println();
        }
    }


    // Create sizes to benchmark
    public static int[] createSizes(int n, int a, int p){
        int[] arr = new int[n];
        for(int i = 0; i<n; i++){
            arr[i] = (i+1)*(int)Math.pow(a, p);
        }
        return arr;
    }


    // Creates values for testing
    public static int[] createValues(int n){
        Random rnd = new Random();
        int[] arr = new int[n];
        for(int i = 0; i<n; i++){
            arr[i] = rnd.nextInt();
        }
        return arr;
    }
}

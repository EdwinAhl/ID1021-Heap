public class ListHeap1 implements Heap{

    Node start;


    @Override public int add(int value){  // O(1)
        start = new Node(value, start);  // Add node to start
        return 0;
    }

    @Override public Integer remove(){  // O(n)
        if(start!=null){  // If list isn't empty
            Node highest_prv = start;  // Node to left of highest
            Node highest = start;  // Highest value of nodes holder
            Node curr  = start;  // Current node
            while(curr.right!=null){  // While next isn't null
                if(curr.right.value > highest.value){  // If next > highest
                    highest_prv = curr;
                    highest = curr.right;
                }
                curr = curr.right;  // Move to next node
            }
            if(highest == start)  // If highest is the first element
                start = highest.right;  // Move start one step
            else  // Highest isn't the first element
                highest_prv.right = highest.right;  // Remove highest
            return highest.value;
        }
        return null;  // Empty
    }

    @Override
    public double[] bench(int[] values) {
        double[] results = new double[2];
        long t0 = System.nanoTime();
        for(int i : values){
            add(i);
        }
        long t1 = System.nanoTime();
        results[0] = t1-t0;

        t0 = System.nanoTime();
        while(start!=null){
            remove();
        }
        t1 = System.nanoTime();
        results[1] = t1-t0;
        return results;
    }
}

public class ArrayHeap implements Heap{

    Integer[] heap;
    int last_pos;

    public ArrayHeap(int size){
        last_pos = -1;
        this.heap = new Integer[size];
    }


    @Override public int add(int value){
        if(last_pos<0){  // Empty
            heap[0] = value;
            last_pos = 0;
            return 0;
        }
        heap[++last_pos] = value;  // Add last
        int current_pos = last_pos;  // Start at last
        int current_value = value;  // Value of last
        int parent_pos = current_pos%2 == 0 ? (current_pos-2)/2 : (current_pos-1)/2;
        int parent_value = heap[parent_pos];  // Parent value

        while(current_value < parent_value){  // If value needs to move up

            // Switch values
            heap[parent_pos] = current_value;
            heap[current_pos] = parent_value;

            // New pos and values
            current_pos = parent_pos;
            if (current_pos == 0) {
                return 0;
            }
            current_value = heap[current_pos];
            parent_pos = current_pos % 2 == 0 ? (current_pos - 2) / 2 : (current_pos - 1) / 2;
            parent_value = heap[parent_pos];
        }
        return 0;


        /*
        int i = 0;
        int current_value = value;
        while(true){
            if(heap[i] == null){  // If null at i, add
                heap[i] = current_value;
                last_pos = i;
                return 0;
            }
            else if(current_value < heap[i]){  // Swaps values if smaller
                Integer tmp = heap[i];
                heap[i] = current_value;
                current_value = tmp;
            }
            else{
                if(heap[i*2+1] == null){  // Left null, add
                    last_pos = i*2+1;
                    heap[i*2+1] = current_value;
                    return 0;
                }
                else if(heap[i*2+2] == null){  // Right null, add
                    last_pos = i*2+2;
                    heap[i*2+2] = current_value;
                    return 0;
                }
                else{  // Increment i
                    i++;
                }
            }
        }
         */
    }


    @Override public Integer remove(){
        if(last_pos<0)  // Empty, do nothing
            return null;
        Integer value = heap[0];
        heap[0] = heap[last_pos];
        int current_pos = 0;
        int left_pos = current_pos*2+1;
        int right_pos = current_pos*2+2;
        while(left_pos <= last_pos && right_pos <= last_pos){  // Move to right position
            if(heap[current_pos] <= heap[left_pos] && heap[current_pos] <= heap[right_pos])  // Right position
                break;
            else if(heap[left_pos] <= heap[right_pos]){  // Swap with left child
                Integer tmp = heap[current_pos];
                heap[current_pos] = heap[left_pos];
                heap[left_pos] = tmp;
                current_pos = left_pos;
            }
            else if(heap[left_pos] > heap[right_pos]){  // Swap with right child
                Integer tmp = heap[current_pos];
                heap[current_pos] = heap[right_pos];
                heap[right_pos] = tmp;
                current_pos = right_pos;
            }
            left_pos = current_pos*2+1;
            right_pos = current_pos*2+2;
        }
        heap[last_pos] = null;  // Remove prev last element
        last_pos--;  // Decrease
        return value;
    }


    @Override public double[] bench(int[] values) {

        // Add
        double[] results = new double[2];
        long t0 = System.nanoTime();
        for(int i : values){
            add(i);
        }
        long t1 = System.nanoTime();
        results[0] = t1-t0;

        // Remove
        t0 = System.nanoTime();
        for(int i : values){
            remove();
        }
        t1 = System.nanoTime();
        results[1] = t1-t0;

        return results;
    }
}
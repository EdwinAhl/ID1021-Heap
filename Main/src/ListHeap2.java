public class ListHeap2 implements Heap{

    Node start;


    @Override public int add(int value){  // O(n)
        Node node = new Node(value);  // Node to be added
        if(start!=null){  // If list isn't empty
            if(value >= start.value){  // If value larger than largest in list (start)
                node.right = start;
                start = node;
            }
            else{  // Value smaller than starts
                Node prev = start;  // Previous node
                Node curr = start.right;  // Current node
                while(curr!=null){  // Don't go past end
                    if(value > curr.value){  // Value larger than current, insert node
                        prev.right = node;
                        node.right = curr;
                        break;
                    }
                    prev = curr;  // Increment prev
                    curr = curr.right;  // Increment curr
                }
                if(node.right!=prev){  // If didn't insert node
                    prev.right = node;  // Add node to end
                }
            }
        }
        else{  // List empty, node is start
            start = node;
        }
        return 0;
    }


    @Override public Integer remove(){  // O(1)
        Integer value = null;  // Value to be returned
        if(start!=null){  // If list isn't empty
            value = start.value;  // Select value to be returned
            start = start.right;  // Move list
        }
        return value;
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

public class TreeHeap implements Heap {

    Node root;  // Start


    @Override
    public int add(int value) {  // Adds a node with value to tree
        int depth = 0;
        if (this.root == null) {  // Heap is empty
            this.root = new Node(value);  // Add value to root
        }
        else {  // Heap is not empty
            Node curr_node = root;  // Selected node
            int curr_value = value;  // Selected value
            while (true) {  // Go through the tree til leaf
                if (curr_value < curr_node.value) {  // Swaps values
                    int tmp = curr_node.value;
                    curr_node.value = curr_value;
                    curr_value = tmp;
                }
                else {  // Value larger, go down
                    curr_node.size++;
                    depth++;
                    if (curr_node.left == null) {  // Add to left side
                        curr_node.left = new Node(curr_value);
                        return depth;
                    }
                    else if (curr_node.right == null) {  // Add to right side
                        curr_node.right = new Node(curr_value);
                        return depth;
                    }
                    if (curr_node.left.size < curr_node.right.size) {  // Balance out left side
                        curr_node = curr_node.left;
                    }
                    else {  // Balance out right side
                        curr_node = curr_node.right;
                    }
                }
            }
        }
        return depth;
    }

    public Integer remove() {
        if (root != null) {
            Integer r = root.value;
            root = removeRecursive(root);
            return r;
        } else {
            return null;
        }
    }
    public Node removeRecursive(Node curr) {
        curr.size--;  // Lower size of current since will remove
        if (curr.left == null) {  // Left side empty, go right
            curr = curr.right;
            return curr;
        } else if (curr.right == null) {  // Right side empty, go left
            curr = curr.left;
            return curr;
        } else if (curr.left.value > curr.right.value) {  // Promote right
            curr.value = curr.right.value;
            curr.right = removeRecursive(curr.right);
            return curr;
        } else if (curr.left.value < curr.right.value) {  // Promote left
            curr.value = curr.left.value;
            curr.left = removeRecursive(curr.left);
            return curr;
        }
        return curr;
    }


    public int push(Integer incr) {
        int depth = 0;  // Keeps track of layer
        Node curr = root;  // Root is starting current
        root.value += incr;  // Increments root value
        int value = root.value;
        while (true) {
            boolean left_smaller;
            if(curr.left != null && curr.right != null){  // Left and right not empty
                if (!(curr.left.value < value || curr.right.value < value)) {  // None is smaller
                    return depth;
                }
                left_smaller = curr.left.value < curr.right.value;  // Left smaller or right smaller
            }
            else left_smaller = curr.left != null;  // If left exist
            depth++;
            if(left_smaller){  // Go left
                value = curr.value;
                curr.value = curr.left.value;
                curr.left.value = value;
                value = curr.value;
                curr = curr.left;
            }
            else if (curr.right != null){  // Go right
                value = curr.value;
                curr.value = curr.right.value;
                curr.right.value = value;
                value = curr.value;
                curr = curr.right;
            }
            else{  // Both null
                return depth;
            }
        }
    }


    // Useless
    @Override public double[] bench(int[] values){
        return null;
    }
}
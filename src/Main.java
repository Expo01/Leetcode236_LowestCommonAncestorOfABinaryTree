import javax.swing.tree.TreeNode;
import java.util.*;

// one of the top solutions. slim, but harder to explain?  makes sense whn i read through it with an example
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) return null;

        if (root == p) return p;

        if (root == q ) return q;

        TreeNode lef = lowestCommonAncestor(root.left,p,q);
        TreeNode rig = lowestCommonAncestor(root.right,p,q);

        if (lef == null)
            return rig;
        if (rig == null)
            return lef;

        return root;
    }
}



// i generally don't like ternary syntax and using 'this' all the time. but 'this' just refers to the object instance of
// solution class where 'this.null' says we are calling the field 'ans' for this object
// editorial recursive solution
class Solution {

    private TreeNode ans;

    public Solution() { // constructor
        this.ans = null; // Variable to store LCA node.
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        this.recurseTree(root, p, q);   // Traverse the tree
        return this.ans;
    }

    private boolean recurseTree(TreeNode currentNode, TreeNode p, TreeNode q) {

        if (currentNode == null) { // If reached the end of a branch, return false.
            return false;
        }

        int left = this.recurseTree(currentNode.left, p, q) ? 1 : 0;  // Left Recursion. If left recursion returns true, set left = 1 else 0
        int right = this.recurseTree(currentNode.right, p, q) ? 1 : 0; // Right Recursion
        int mid = (currentNode == p || currentNode == q) ? 1 : 0; // If the current node is one of p or q

        if (mid + left + right >= 2) { // If any two of the flags left, right or mid become True
            this.ans = currentNode;
        }

        return (mid + left + right > 0); // Return true if any one of the three bool values is True.
    }
} // this will traverse all elements of tree. even after this.ans assigned and answer found.



// iterative but SUPER slow and bad memory
//class Solution {
//
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//
//        Deque<TreeNode> stack = new ArrayDeque<>();  // Stack for tree traversal
//        Map<TreeNode, TreeNode> parent = new HashMap<>();   // HashMap for parent pointers
//
//        parent.put(root, null);
//        stack.push(root);
//
//        while (!parent.containsKey(p) || !parent.containsKey(q)) { // Iterate until we find both the nodes p and q
//            TreeNode node = stack.pop();
//
//            if (node.left != null) {   // While traversing the tree, keep saving the parent pointers.
//                parent.put(node.left, node);
//                stack.push(node.left);
//            }
//            if (node.right != null) {
//                parent.put(node.right, node);
//                stack.push(node.right);
//            }
//        }
//
//        Set<TreeNode> ancestors = new HashSet<>(); // Ancestors set() for node p.
//        while (p != null) { // Process all ancestors for node p using parent pointers.
//            ancestors.add(p);
//            p = parent.get(p);
//        }
//
//        while (!ancestors.contains(q))  // The first ancestor of q which appears in p's ancestor set() is their lowest common ancestor.
//            q = parent.get(q);
//        return q;
//    }
//
//}

// MY ATTEMPT
//class Solution {
//    Stack<TreeNode> pStack = new Stack<>();
//    Stack<TreeNode> qStack = new Stack<>();
//
//    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
//        DFSPreOrder(root,p,pStack);
//        DFSPreOrder(root,q,qStack);
//        while(pStack.size() != qStack.size()){
//            if(pStack.size() > qStack.size()){
//                pStack.pop();
//            }else {
//                qStack.pop();
//            }
//        }
//        while(!pStack.isEmpty() && !qStack.isEmpty()){
//            if(pStack.peek().equals(qStack.peek())){
//                return pStack.peek();
//            } else {
//                pStack.pop();
//                qStack.pop();
//            }
//        }
//        return null;
//    }
//
//    private Stack<TreeNode> DFSPreOrder(TreeNode root, TreeNode target, Stack<TreeNode> currStack){
//        TreeNode curr = root;
//        boolean flag = false;
//        if (curr != null){
//            currStack.add(curr);
//            if(curr.equals(target)){
//                flag = true;
//                return currStack;
//            }
//            DFSPreOrder(curr.left,target, currStack);
////            if(flag){ return currStack;} // when left branch returns target, it will still search R branch then
////            // rest of tree.... how do I prevent this?
//            DFSPreOrder(curr.right, target, currStack);
//        }
//        return currStack;
//    }
//}
// A: 3, 5 return A
// B: 3, 5, 6, 2, 7, 4, 1 return B


// this will be the most immediate lesser node, can't find first common ancestor and
// then just keep working all the way to the root or something (assuming root is say MIN in theory)

// two calls. have an "A" pointer and "B" pointer for p and q. add items to their respective stacks
// until p and q found. then while one stack > othr in size, pop items. then compare if popped
// nodees aree ==

// p = 5 , q = 1
// A stack: [3,5]
// B stack: [3,1]
// stacks == size, pop bothh. 5 != 1. --> 3==3. return 3

// p = 5, q = 4
// A stack: [3,5]
// B stack: [3,5,6,2,7,4]
// pop B stack items until both stacks [3,5] then 5==5 return 5
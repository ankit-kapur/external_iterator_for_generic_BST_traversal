import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Stack<T extends Comparable<T>> {

	public Map<Integer, Tree<T>> stack;
	public int stackPointer;

	public Stack() {
		stack = new HashMap<Integer, Tree<T>>();
		stackPointer = -1;
	}

	public int getStackPointerPosition() {
		return stackPointer;
	}

	public void push(Tree<T> s) {
		if (stack != null) {
			stack.put(++stackPointer, s);
		}
	}

	public Tree<T> pop() {
		if (!isEmpty()) {
			return stack.get(stackPointer--);
		} else {
			return null;
		}
	}

	public boolean isEmpty() {
		return (stackPointer < 0 ? true : false);
	}

	public Tree<T> getTopElement() {
		if (!isEmpty()) {
			return stack.get(stackPointer);
		} else {
			return null;
		}
	}
}

/* Tree implements the binary search tree property */
class Tree<T extends Comparable<T>> {
	public Tree(T v) {
		value = v;
		left = null;
		right = null;
	}

	public void insert(T v) {
		if (value.compareTo(v) == 0)
			return;
		if (value.compareTo(v) > 0)
			if (left == null)
				left = new Tree<T>(v);
			else
				left.insert(v);
		else if (value.compareTo(v) < 0)
			if (right == null)
				right = new Tree<T>(v);
			else
				right.insert(v);
	}

	protected T value;
	protected Tree<T> left;
	protected Tree<T> right;
}

/* Define here the external iterator operations, done and next */
class Iter<T extends Comparable<T>> {

	protected Tree<T> currentNode;
	Stack<T> stack;
	List<Tree<T>> archiveList;

	Iter(Tree<T> rootNode) {

		stack = new Stack<T>();
		archiveList = new ArrayList<Tree<T>>();

		currentNode = rootNode;
		stack.push(rootNode);
	}

	/* Done */
	public boolean done() {
		boolean isDone = true;
		if (stack != null && !stack.isEmpty())
			isDone = false;
		return isDone;
	}

	/* Next */
	public T next() {
		T value = null;
		if (!done()) {
			currentNode = stack.getTopElement();

			if (currentNode == null) {
				done();
			}

			if (!archiveList.contains(currentNode)) {
				while (currentNode.left != null) {
					archiveList.add(currentNode);
					currentNode = currentNode.left;
					stack.push(currentNode);
				}
			}

			value = currentNode.value;
			currentNode = stack.pop();

			if (currentNode != null && currentNode.right != null)
				stack.push(currentNode.right);
		}
		return value;
	}
}

public class GenericTreeEquality {

	static <T extends Comparable<T>> boolean equals(Tree<T> tree1, Tree<T> tree2) {
		boolean treeAreEqual = true;

		/* Define here the equality test */
		Iter<T> treeIter1 = new Iter<T>(tree1);
		Iter<T> treeIter2 = new Iter<T>(tree2);
		
		while (true) {
			int value1 = -1, value2 = -1;
			if (!treeIter1.done()) {
				value1 = (Integer) treeIter1.next();
			} else if (!treeIter2.done()) {
				treeAreEqual = false;
				break;
			} else {
				break;
			}
			
			if (!treeIter2.done()) {
				value2 = (Integer) treeIter2.next();
			}
			
			if (value1 != value2) {
				treeAreEqual = false;
				break;
			}
		}
		
		return treeAreEqual;
	}

	static <T extends Comparable<T>> void printInorder(Tree<T> tree1) {
		Iter<T> treeIter = new Iter<T>(tree1);
		while (!treeIter.done()) {
			Integer value = (Integer) treeIter.next();
			System.out.print(value + "  ");
		}

	}

	public static void main(String[] args) {

		Tree<Integer> tree1 = new Tree<Integer>(50);
		tree1.insert(70);
		tree1.insert(80);
		tree1.insert(90);
		tree1.insert(100);

		Tree<Integer> tree2 = new Tree<Integer>(100);
		tree2.insert(90);
		tree2.insert(80);
		tree2.insert(70);
		tree2.insert(50);

		System.out.println(equals(tree1, tree2));

		// Tree<Integer> tree3 = new Tree<Integer>(9);
		// tree3.insert(3);
		// tree3.insert(2);
		// tree3.insert(1);
		// tree3.insert(5);
		// tree3.insert(4);
		// tree3.insert(7);
		// tree3.insert(11);
		// tree3.insert(15);
		// tree3.insert(14);
		//
		// printInorder(tree3);
	}
}
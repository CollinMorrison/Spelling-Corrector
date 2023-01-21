package spell;

public class Node implements INode{

    public Node() {
        this.counter = 0;
        this.nodes = new Node[26];
    }

    private int counter;
    private Node[] nodes;

    @Override
    public int getValue() {
        return this.counter;
    }

    @Override
    public void incrementValue() {
        this.counter++;
    }

    @Override
    public Node[] getChildren() {
        return this.nodes;
    }

    public void setNode(Node newNode, int indexToAdd) {
        this.nodes[indexToAdd] = newNode;
    }
}

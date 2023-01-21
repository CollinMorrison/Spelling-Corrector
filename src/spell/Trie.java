package spell;

public class Trie implements spell.ITrie {

    public Trie() {
        this.root = new Node();
    }

    private Node root;
    @Override
    public void add(String word) {
        Node currentNode = this.root;
        char currentLetter;
        int indexOfChar;
        //convert word to lower case
        word = word.toLowerCase();

        //Process each character, one at a time
        for (int i = 0; i < word.length(); ++i) {
            //Start with the first character in the word
            currentLetter = word.charAt(i);
            //System.out.println("Current Letter: " + currentLetter + "\n");
            //gives the offset from 'a' our current character is at
            indexOfChar = currentLetter - 'a';
            //if the node doesn't exist yet, add it
            if (currentNode.getChildren()[indexOfChar] == null) {
                //Start inserting at the root
                currentNode.setNode(new Node(), indexOfChar);
                currentNode = currentNode.getChildren()[indexOfChar];
            } else {
                currentNode = currentNode.getChildren()[indexOfChar];
            }
            //if we are at the last character in the word, update its counter
            if (i == word.length() - 1) {
                currentNode.incrementValue();
            }
        }
        //System.out.println("Word Count: " + currentNode.getValue() + "\n");
    }

    @Override
    public Node find(String word) {
        return new Node();
    }

    @Override
    public int getWordCount() {
        return 0;
    }

    @Override
    public int getNodeCount() {
        return 0;
    }

    /**
     * must be recursive
     * Traverses the tree in alphabetical order, returning a string with the format <word>\n<word>\n...
     * @return
     */
    @Override
    public String toString() {
        StringBuilder currentWord = new StringBuilder();
        StringBuilder output = new StringBuilder();
        toStringHelper(this.root, currentWord, output);

        return output.toString();
    }

    private void toStringHelper(Node currentNode, StringBuilder currentWord, StringBuilder output) {
        if (currentNode.getValue() > 0) {
            output.append(currentWord.toString());
            output.append("\n");
        }
        for (int i = 0; i < currentNode.getChildren().length; ++i) {
            Node child = currentNode.getChildren()[i];
            if (child != null) {
                char childLetter = (char)('a' + i);
                currentWord.append(childLetter);
                toStringHelper(child, currentWord, output);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }
        }
    }


    public int hashCode() {

        return 0;
    }

    /**
     * must be recursive
     * @param trieToTest
     * @return
     */
    public boolean equals(Trie trieToTest) {

        return true;
    }

    //TODO: ToString() method
    //TODO: HashCode() method
    //TODO: Equals() method
}

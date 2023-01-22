package spell;

public class Trie implements spell.ITrie {

    public Trie() {
        this.root = new Node();
        this.wordCount = 0;
        this.nodeCount = 1;
    }

    private Node root;
    private int wordCount;
    private int nodeCount;
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
                ++this.nodeCount;
            } else {
                currentNode = currentNode.getChildren()[indexOfChar];
            }
            //if we are at the last character in the word, update its counter
            if (i == word.length() - 1) {
                currentNode.incrementValue();
                if (currentNode.getValue() == 1) {
                    ++this.wordCount;
                }
            }
        }
        //System.out.println("Word Count: " + currentNode.getValue() + "\n");
    }

    @Override
    /**
     * returns the final node of the word
     */
    public Node find(String word) {
        //make the word lower case
        boolean wordExists = true;
        word = word.toLowerCase();
        Node currentNode = this.root;
        int currentIndex = 0;
        char currentLetter;
        //iterate through the trie for the length of the word
        for (int i = 0; i < word.length(); ++i) {
            currentLetter = word.charAt(i);
            currentIndex = currentLetter - 'a';
            //if the letter exists, move down to that node
            if (currentNode.getChildren()[currentIndex] != null) {
                currentNode = currentNode.getChildren()[currentIndex];
            } else {
                wordExists = false;
            }
            //if we're at the last letter of the word and the word count is greater than 0, and the word was never different
            if (i == word.length() - 1 && currentNode.getValue() > 0 && wordExists) {
                return currentNode;
            }
        }
        return null;
    }

    @Override
    public int getWordCount() {
        return this.wordCount;
    }

    @Override
    public int getNodeCount() {
        return this.nodeCount;
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

    @Override
    public int hashCode() {
        int hashCode = 0;
        int nnullChildren = 0;
        hashCode = (this.wordCount * this.nodeCount);
        for (int i = 0; i < this.root.getChildren().length; ++i) {
            if (this.root.getChildren()[i] != null) {
                hashCode += i;
                ++nnullChildren;
            }
        }
        hashCode /= nnullChildren;
        return hashCode;
    }

    /**
     * must be recursive
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        //if o == null, return false
        if (o == null) {
            return false;
        }
        //if o == this, return true ie. o and this are referencing the same object in memory
        if (o == this) {
            return true;
        }
        //do this and o have the same class? if no return false, else keep going
        if (!(o instanceof Trie)) {
            return false;
        }
        //cast o to a trie
        Trie t = (Trie) o;
        // do this and t have the same wordCount and nodeCount?
        if (this.nodeCount != t.getNodeCount() || this.wordCount != t.getWordCount()) {
            return false;
        }

        return equalsHelper(this.root, t.root);
    }

    private boolean equalsHelper(Node n1, Node n2) {
        //compare n1 and n2 to see if they are the same
            //Do n1 and n2 have the same count? If not, return false
        if (n1.getValue() != n2.getValue()) {
            return false;
        }
        //Do they have non-null children in the same positions in the child arrays?
        //Make sure the children arrays are the same length
        if (n1.getChildren().length != n2.getChildren().length) {
            return false;
        }
        //for each child
        for (int i = 0; i < n1.getChildren().length; ++i) {
            //if one node is null but the other isn't, return false. ie. checking that the characters match
            if ((n1.getChildren()[i] == null && n2.getChildren()[i] != null)
                    || (n1.getChildren()[i] != null && n2.getChildren()[i] == null)) {
                return false;
            }
            //if the matching nodes are not null, evaluate their sub-trees
            if (n1.getChildren()[i] != null && n2.getChildren()[i] != null) {
                // Recurse on the children and compare the child subtrees
                Node n3 = n1.getChildren()[i];
                Node n4 = n2.getChildren()[i];
                if (!equalsHelper(n3, n4)) {
                    return false;
                }
            }
        }
        return true;
    }
}

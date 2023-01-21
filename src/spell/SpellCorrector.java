package spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {

    /**
     * passes each individual word in the dictionary to the trie for storage
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException
     */
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        Trie trie = new Trie();
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String str = scanner.next();
            //System.out.println(str);
            trie.add(str);
            //TODO: this is where you do what ever you want to do with the individual word, ie. add it to the Trie

        }
        System.out.println(trie.toString());
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        return null;
    }
}

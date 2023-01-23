package spell;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {

    public SpellCorrector() {
        this.trie = new Trie();
    }

    private Trie trie;

    /**
     * passes each individual word in the dictionary to the trie for storage
     * @param dictionaryFileName the file containing the words to be used
     * @throws IOException
     */
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File file = new File(dictionaryFileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String str = scanner.next();
            this.trie.add(str);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        if (this.trie.find(inputWord) != null) {
            return inputWord;
        }

        Set<String> editDistanceOneSet = generateEditDistanceOneWords(inputWord);
        Vector<String> solutions = generateSolutions(editDistanceOneSet);

        if (solutions.size() == 1) {
            return solutions.elementAt(0);
        } else if (solutions.size() == 0) {
            Set<String> editDistanceTwoSet = generateEditDistanceTwoWords(editDistanceOneSet);
            //solutions = generateSolutions(editDistanceTwoSet);
        } else {
            //use the word with the highest frequency count, then use the tie breakers
        }


        return null;
    }

    private Set<String> generateEditDistanceOneWords (String inputWord) {
        Set<String> wordSet = new HashSet<>();
        wordSet.addAll(swapLetters(inputWord));
        wordSet.addAll(dropLetters(inputWord));
        wordSet.addAll(insertExtraLetter(inputWord));
        wordSet.addAll(swapAdjacentLetters(inputWord));
        return wordSet;
    }

    private Vector<String> generateSolutions (Set<String> editDistanceWords) {
        Vector<String> solutions = new Vector<>();
        for (String currentWord : editDistanceWords) {
            if (this.trie.find(currentWord) != null) {
                solutions.add(currentWord);
            }
        }
        return solutions;
    }

    private Set<String> generateEditDistanceTwoWords (Set<String> editDistanceOneWords) {
        Set<String> editDistanceTwoWords = new HashSet<>();
        for (String editDistanceOneWord : editDistanceOneWords) {
            for (String editDistanceTwoWord : generateEditDistanceOneWords(editDistanceOneWord)) {
                editDistanceTwoWords.add(editDistanceTwoWord);
            }
        }
        return editDistanceTwoWords;
    }

    private Set<String> swapLetters(String inputWord) {
        //System.out.println("swapped letters: ");
        Set<String> words = new HashSet<>();
        int indexToIgnore = 0;
        for (int i = 0; i < inputWord.length(); ++i) {
            StringBuilder newWord = new StringBuilder(inputWord);
            indexToIgnore = inputWord.charAt(i) - 'a';
            for (int j = 0; j < 26; ++j) {
                if (j != indexToIgnore) {
                    newWord.setCharAt(i, (char)('a' + j));
                    words.add(newWord.toString());
                    //System.out.println(newWord.toString());
                }
            }
        }
        return words;
    }

    private Set<String> dropLetters (String inputWord) {
        Set<String> words = new HashSet<>();
        for (int i = 0; i < inputWord.length(); ++i) {
            StringBuilder newWord = new StringBuilder(inputWord);
            newWord.deleteCharAt(i);
            words.add(newWord.toString());
        }
        return words;
    }

    private Set<String> insertExtraLetter (String inputWord) {
       // System.out.println("InsertExtraLetter: ");
        Set<String> words = new HashSet<>();
        for (int i = 0; i < inputWord.length() + 1; ++i) {
            StringBuilder newWord = new StringBuilder(inputWord);
            newWord.insert(i, 'a');
            for (int j = 0; j < 26; ++j) {
                newWord.setCharAt(i, (char)(j + 'a'));
                words.add(newWord.toString());
                //System.out.println(newWord.toString());
            }
        }
        return words;
    }

    private Set<String> swapAdjacentLetters(String inputWord) {
        Set<String> words = new HashSet<>();
        char tempChar;
        for (int i = 0; i < inputWord.length(); ++i) {
            StringBuilder newWord = new StringBuilder(inputWord);
            for (int j = 1; j < inputWord.length(); ++j) {
                if (j == i + 1) {
                    tempChar = newWord.charAt(i);
                    newWord.setCharAt(i, newWord.charAt(j));
                    newWord.setCharAt(j, tempChar);
                    words.add(newWord.toString());
                }
            }
        }
        return words;
    }
}

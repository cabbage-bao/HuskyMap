package deques.palindrome;

import deques.Deque;
import deques.LinkedDeque;

public class Palindrome {

    public Deque<Character> wordToDeque(String word) {
        LinkedDeque<Character> deque = new LinkedDeque<>();
        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }
        return deque;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean isPalindrome(String word) {
        int end = word.length() - 1;
        int start = 0;
        if (end == -1 || end == 0) {
            return true;
        }
        Deque<Character> temp = wordToDeque(word);
        while (start < end) {
            if (temp.removeFirst() != temp.removeLast()) {
                return false;
            }
            start++;
            end--;
        }
        return true;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int end = word.length() - 1;
        int start = 0;
        if (end == -1 || end == 0) {
            return true;
        }
        Deque<Character> temp = wordToDeque(word);
        while (start < end) {
            if (!cc.equalChars(temp.removeFirst(), temp.removeLast())) {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }
}

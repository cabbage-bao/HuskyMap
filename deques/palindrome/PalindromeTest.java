package deques.palindrome;

import deques.Deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator obo = new OffByOne();


    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {

        assertEquals(palindrome.isPalindrome("a"), true);
        assertEquals(palindrome.isPalindrome(""), true);
        assertTrue(palindrome.isPalindrome("aca"));
        assertEquals(palindrome.isPalindrome("asffa"), false);
        assertEquals(palindrome.isPalindrome("ASffa"), false);
        assertEquals(palindrome.isPalindrome("aabbaa"), true);
        assertEquals(palindrome.isPalindrome("AABBAA"), true);
        assertEquals(palindrome.isPalindrome("Aa"), true);
    }

    @Test
    public void test2Palindrome() {

        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome("", obo));
        assertTrue(palindrome.isPalindrome("acfgdb", obo));
        assertTrue(palindrome.isPalindrome("acftgdb", obo));
        assertTrue(palindrome.isPalindrome("4123", obo));
        assertTrue(palindrome.isPalindrome("41523", obo));
        assertTrue(palindrome.isPalindrome("ACDB", obo));

        assertFalse(palindrome.isPalindrome("22222", obo));
        assertFalse(palindrome.isPalindrome("asfsadg", obo));
        assertFalse(palindrome.isPalindrome("ADSFasd", obo));
    }
}

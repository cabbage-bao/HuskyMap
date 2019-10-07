package deques.palindrome;

import org.junit.Test;

import static org.junit.Assert.*;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void oboTest() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'd'));

        assertTrue(offByOne.equalChars('A', 'B'));
        assertFalse(offByOne.equalChars('A', 'b'));

        assertTrue(offByOne.equalChars('1', '2'));
        assertFalse(offByOne.equalChars('3', '5'));
    }

}

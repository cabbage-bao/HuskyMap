package autocomplete;

//import java.util.ArrayList;

//import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.List;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] myTerm;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
        if (terms == null || Arrays.asList(terms).contains(null)) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(terms);
        this.myTerm = terms;
    }

    /**
     * Returns all terms that start with the given prefix, in descending order of weight.
     * @throws IllegalArgumentException if prefix is null
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }

        int len = prefix.length();

        int start = 0;
        int end = myTerm.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (myTerm[mid].queryPrefix(len).compareTo(prefix) >= 0) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        int first = start;            //return the first position of term with prefix

        start = 0;
        end = myTerm.length - 1;
        while (start < end) {
            int mid = (start + end) / 2 + 1;
            if (myTerm[mid].queryPrefix(len).compareTo(prefix) <= 0) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        int last = start;            //return the last position of term with prefix

        Term[] ans = new Term[last - first + 1];
        for (int i = first; i <= last; i++) {
            ans[i - first] = myTerm[i];
        }
        Arrays.sort(ans, TermComparators.byReverseWeightOrder());
        return ans;

    }
}

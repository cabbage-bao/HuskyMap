package autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BinaryRangeSearch implements Autocomplete {
    private Term[] myTerm;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public BinaryRangeSearch(Term[] terms) {
        if (terms == null || terms.length == 0) {
            throw new IllegalArgumentException();
        }

        Arrays.sort(terms);
        this.myTerm = terms;
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
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
        List<Term> list = new ArrayList<>();
        for (Term temp : this.myTerm) {
            list.add(temp);
        }

        int start = 0;
        int end = myTerm.length - 1;
        while (start < end) {
            int mid = (start + end) / 2;
            if (list.get(mid).queryPrefix(len).compareTo(prefix) >= 0) {
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
            if (list.get(mid).queryPrefix(len).compareTo(prefix) <= 0) {
                start = mid;
            } else {
                end = mid - 1;
            }
        }
        int last = start;            //return the last position of term with prefix

        List<Term> sub = new ArrayList<>();
        for (int i = first; i <= last; i++) {
            sub.add(list.get(i));
        }
        sub.sort(TermComparators.byReverseWeightOrder());   //sort the subList in descending order of weight
        Term[] ans = sub.toArray(new Term[sub.size()]);
        return ans;
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }
}

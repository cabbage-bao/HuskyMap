package autocomplete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearRangeSearch implements Autocomplete {
    private Term[] myTerm;

    /**
     * Validates and stores the given array of terms.
     * Assumes that the given array will not be used externally afterwards (and thus may directly
     * store and mutate it).
     * @throws IllegalArgumentException if terms is null or contains null
     */
    public LinearRangeSearch(Term[] terms) {
        if (terms == null || Arrays.asList(terms).contains(null)) {
            throw new IllegalArgumentException();
        }
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

        List<Term> res = new ArrayList<>();
        for (Term temp : this.myTerm) {
            if (temp.queryPrefix(prefix.length()).equals(prefix)) {
                res.add(temp);
            }
        }
        res.sort(TermComparators.byReverseWeightOrder());
        Term[] ans = res.toArray(new Term[res.size()]);
        return ans;
    }
}


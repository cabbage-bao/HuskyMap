package autocomplete;

import java.util.Objects;

public class Term implements Comparable<Term> {

    private String str;
    private long weight;
    /**
     * Initializes a term with the given query string and weight.
     * @throws IllegalArgumentException if query is null or weight is negative
     */
    public Term(String query, long weight) {
        this.str = query;
        this.weight = weight;
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /**
     * Compares the two terms in lexicographic order by query.
     * @throws NullPointerException if the specified object is null
     */
    public int compareTo(Term that) {
        return this.str.compareTo(that.str);
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /** Compares to another term, in descending order by weight. */
    public int compareToByReverseWeightOrder(Term that) {
        if (this.weight == that.weight) {
            return this.str.compareTo(that.str);
        }
        return this.weight > that.weight ? -1 : 1;
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /**
     * Compares to another term in lexicographic order, but using only the first r characters
     * of each query. If r is greater than the length of any term's query, compares using the
     * term's full query.
     * @throws IllegalArgumentException if r < 0
     */
    public int compareToByPrefixOrder(Term that, int r) {
        if (r > this.str.length() || r > that.str.length()) {
            return this.compareTo(that);
        }
        return this.str.substring(0, r).compareTo(that.str.substring(0, r));

        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }
    /** Returns this term's query. */
    public String query() {
        return this.str;
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /**
     * Returns the first r characters of this query.
     * If r is greater than the length of the query, returns the entire query.
     * @throws IllegalArgumentException if r < 0
     */
    public String queryPrefix(int r) {
        if (r >= this.str.length()) {
            return this.str;
        }
        return this.str.substring(0, r);
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    /** Returns this term's weight. */
    public long weight() {
        return this.weight;
        //throw new UnsupportedOperationException("Not implemented yet: replace this with your code.");
    }

    @Override
    public String toString() {
        return "Term{" +
                "str='" + str + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Term term = (Term) o;
        return weight == term.weight &&
                Objects.equals(str, term.str);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str, weight);
    }
}

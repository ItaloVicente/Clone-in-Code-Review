 * Arbitrary sequence of elements with fast comparison support.
 * <p>
 * A sequence of elements is defined to contain elements in the index range
 * <code>[0, {@link #size()})</code>, like a standard Java List implementation.
 * Unlike a List, the members of the sequence are not directly obtainable, but
 * element equality can be tested if two Sequences are the same implementation.
 * <p>
 * An implementation may chose to implement the equals semantic as necessary,
 * including fuzzy matching rules such as ignoring insignificant sub-elements,
 * e.g. ignoring whitespace differences in text.
 * <p>
 * Implementations of Sequence are primarily intended for use in content
 * difference detection algorithms, to produce an {@link EditList} of
 * {@link Edit} instances describing how two Sequence instances differ.

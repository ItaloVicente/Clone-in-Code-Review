 * Layouts that implement this interface are capable of caching the
 * sizes of child controls in a manner that allows the information
 * for a single control to be flushed without affecting the remaining
 * controls. These layouts will ignore the "changed" arguments to layout(...)
 * and computeSize(...), however they will flush their cache for individual
 * controls when the flush(...) method is called.

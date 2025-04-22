 * According to the document
 * the order to find the .lfsconfig file is:
 *
 * <pre>
 *   1. in the root of the working tree
 *   2. in the index
 *   3. in the HEAD, for bare repositories this is the only place
 *      that is searched
 * </pre>
 *
 * Values from the .lfsconfig are used only if not specified in another git
 * config file to allow local override without modifiction of a committed file.

 * A global cache of some state of repositories. The cache is automatically
 * cleared whenever the workbench selection or the menu selection changes.
 * <p>
 * Intended for use in property testers and in other handler activation or
 * enablement code, such as {@code isEnabled()} methods. Using this cache can
 * massively reduce the number of file system accesses done in the UI thread to
 * evaluate some git repository state. The first handler evaluations will fill
 * the cache, and subsequent enablement expressions can then re-use these cached
 * values.
 * </p>

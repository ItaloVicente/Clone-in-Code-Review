 * CacheWrapper creates a Composite which should have exactly one child: the control
 * whose size should be cached. Note that CacheWrapper does NOT respect the flushCache
 * argument to layout() and computeSize(). This is intentional, since the whole point of
 * this class is to workaround layouts with poor caching, and such layouts will typically
 * be too eager about flushing the caches of their children. However, this means that you
 * MUST manually call flushCache() whenver the child's preferred size changes (and before
 * the parent is layed out).

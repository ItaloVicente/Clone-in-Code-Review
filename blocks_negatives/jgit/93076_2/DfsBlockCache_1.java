/**
 * Caches slices of a {@link DfsPackFile} in memory for faster read access.
 * <p>
 * The DfsBlockCache serves as a Java based "buffer cache", loading segments of
 * a DfsPackFile into the JVM heap prior to use. As JGit often wants to do reads
 * of only tiny slices of a file, the DfsBlockCache tries to smooth out these
 * tiny reads into larger block-sized IO operations.
 * <p>
 * Whenever a cache miss occurs, loading is invoked by exactly one thread for
 * the given <code>(DfsPackKey,position)</code> key tuple. This is ensured by an
 * array of locks, with the tuple hashed to a lock instance.
 * <p>
 * Its too expensive during object access to be accurate with a least recently
 * used (LRU) algorithm. Strictly ordering every read is a lot of overhead that
 * typically doesn't yield a corresponding benefit to the application. This
 * cache implements a clock replacement algorithm, giving each block one chance
 * to have been accessed during a sweep of the cache to save itself from
 * eviction.
 * <p>
 * Entities created by the cache are held under hard references, preventing the
 * Java VM from clearing anything. Blocks are discarded by the replacement
 * algorithm when adding a new block would cause the cache to exceed its
 * configured maximum size.
 * <p>
 * The key tuple is passed through to methods as a pair of parameters rather
 * than as a single Object, thus reducing the transient memory allocations of
 * callers. It is more efficient to avoid the allocation, as we can't be 100%
 * sure that a JIT would be able to stack-allocate a key tuple.
 * <p>
 * The internal hash table does not expand at runtime, instead it is fixed in
 * size at cache creation time. The internal lock table used to gate load
 * invocations is also fixed in size.
 */
public final class DfsBlockCache {
	private static volatile DfsBlockCache cache;

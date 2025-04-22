
package org.eclipse.jgit.internal.storage.dfs;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DfsBlockCaffeineCache extends DfsBlockCache {

    private static final int EMPTY_STRING_SIZE = 8;

    private static final int ESTIMATED_EMPTY_ENTRY_SIZE = 60;

    private static final int ESTIMATED_INDEX_SIZE_MULTIPLIER = 18;
    private static final int ESTIMATED_INDEX_SIZE_EXTRA_BYTES = 2076;

    private static final int PLACEHOLDER_POSITION = -100;

    private final long maxStreamThroughCache;

    private final int blockSize;

    private final Map<DfsPackDescription

    private final Set<DfsPackKey> packKeysWithNoCacheEntries;

    private final Map<DfsPackKey

    private final Cache<DfsPackKeyWithPosition

    public static void reconfigure(final DfsBlockCaffeineCacheConfig cacheConfig) {
        DfsBlockCache.setInstance(new DfsBlockCaffeineCache(cacheConfig));
    }

    private DfsBlockCaffeineCache(DfsBlockCaffeineCacheConfig cacheConfig) {
        maxStreamThroughCache = (long) (cacheConfig.getCacheMaximumSize() * cacheConfig.getStreamRatio());

        blockSize = cacheConfig.getBlockSize();

        packFileMap = new ConcurrentHashMap<>(16
        packKeysWithNoCacheEntries = new HashSet<>(16
        reversePackDescriptionIndex = new ConcurrentHashMap<>(16

        long maximumSizeForCaffeinceCache = (cacheConfig.getCacheMaximumSize() / 3) * 2;
        dfsBlockAndIndicesCache = Caffeine.newBuilder()
                .removalListener(this::cleanUpIndices)
                .maximumWeight(maximumSizeForCaffeinceCache)
                .weigher((DfsPackKeyWithPosition keyWithPosition
                        ref == null ? ESTIMATED_EMPTY_ENTRY_SIZE : ESTIMATED_EMPTY_ENTRY_SIZE + ref.getRetainedSize())
                .build();
    }

    private void cleanUpIndices(DfsPackKeyWithPosition keyWithPosition
        if (keyWithPosition != null && ref != null) {
            DfsPackKey key = keyWithPosition.getDfsPackKey();
            long position = keyWithPosition.getPosition();

            if (position == PLACEHOLDER_POSITION) {
                if (key != null && packKeysWithNoCacheEntries.contains(key)) {
                    cleanUpIndicesIfExists(key);
                }
            } else if (position < 0) {
                cleanUpIndicesIfExists(key);
            } else {
                if (key.cachedSize().addAndGet(-ref.getSize()) <= 0) {
                    cleanUpIndicesIfExists(key);
                }
            }
        }
    }

    private void cleanUpIndicesIfExists(DfsPackKey key) {
        if (key != null) {
            packKeysWithNoCacheEntries.remove(key);
            DfsPackDescription description = reversePackDescriptionIndex.remove(key);
            if (description != null) {
                packFileMap.remove(description);
            }
        }
    }

    public boolean shouldCopyThroughCache(long length) {
        return length <= maxStreamThroughCache;
    }

    public DfsPackFile getOrCreate(DfsPackDescription description
        return packFileMap.compute(description
            if (packFile != null && !packFile.invalid()) {
                return packFile;
            }
            DfsPackKey newPackKey = key != null ? key : new DfsPackKey();
            reversePackDescriptionIndex.put(newPackKey
            packKeysWithNoCacheEntries.add(newPackKey);
            dfsBlockAndIndicesCache.put(
                    new DfsPackKeyWithPosition(key
                    new Ref(key
            return new DfsPackFile(this
        });
    }

    public int getBlockSize() {
        return blockSize;
    }

    public DfsBlock getOrLoad(DfsPackFile pack
        long alignedPosition = pack.alignToBlock(position);
        DfsPackKey key = pack.key();

        if (key == null) {
            return null;
        }

        Ref<DfsBlock> loadedBlockRef = dfsBlockAndIndicesCache.get(
                new DfsPackKeyWithPosition(key
                    packKeysWithNoCacheEntries.remove(key);
                    try {
                        DfsBlock loadedBlock = pack.readOneBlock(keyWithPosition.getPosition()
                        key.cachedSize().addAndGet(loadedBlock.size());
                        return new Ref(keyWithPosition.getDfsPackKey()
                                loadedBlock.size()
                    } catch (IOException e) {
                        return null;
                    }
                });

        if (loadedBlockRef != null) {
            DfsBlock loadedBlock = loadedBlockRef.get();
            if (loadedBlock != null && loadedBlock.contains(key
                return loadedBlock;
            }
        }

        dfsBlockAndIndicesCache.invalidate(new DfsPackKeyWithPosition(key
        return getOrLoad(pack
    }

    public void put(DfsBlock block) {
        put(block.packKey()
    }

    public <T> Ref<T> put(DfsPackKey key
        return dfsBlockAndIndicesCache.get(new DfsPackKeyWithPosition(key
            packKeysWithNoCacheEntries.remove(key);
            if (keyWithPosition.position >= 0) {
                keyWithPosition.getDfsPackKey().cachedSize().getAndAdd(size);
            }
            return new Ref(keyWithPosition.getDfsPackKey()
        });
    }

    public boolean contains(DfsPackKey key
        return get(key
    }

    @SuppressWarnings("unchecked")
    public <T> T get(DfsPackKey key
        Ref<T> blockCache = dfsBlockAndIndicesCache.getIfPresent(new DfsPackKeyWithPosition(key
        return blockCache != null ? blockCache.get() : null;
    }

    public void remove(DfsPackFile pack) {
        if (pack != null) {
            DfsPackKey key = pack.key();
            key.cachedSize().set(0);
            cleanUpIndicesIfExists(key);
        }
    }

    public void cleanUp() {
        packFileMap.clear();
        packKeysWithNoCacheEntries.clear();
        reversePackDescriptionIndex.clear();
        dfsBlockAndIndicesCache.invalidateAll();
    }

    private static final class DfsPackKeyWithPosition {
        private final DfsPackKey dfsPackKey;
        private final long position;

        DfsPackKeyWithPosition(DfsPackKey dfsPackKey
            this.dfsPackKey = dfsPackKey;
            this.position = position;
        }

        public DfsPackKey getDfsPackKey() {
            return dfsPackKey;
        }

        public long getPosition() {
            return position;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DfsPackKeyWithPosition)) {
                return false;
            }
            DfsPackKeyWithPosition that = (DfsPackKeyWithPosition) other;
            return Objects.equals(this.getDfsPackKey()
                    && this.getPosition() == that.getPosition();
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.getDfsPackKey()
        }

    }

    public static final class Ref<T> extends DfsBlockCache.Ref<T> {
        private final DfsPackKey key;
        private final long position;
        private final int size;
        private volatile T value;

        public Ref(DfsPackKey key
            this.key = key;
            this.position = position;
            this.size = size;
            this.value = value;
        }

        public T get() {
            return value;
        }

        public boolean has() {
            return value != null;
        }

        public int getSize() {
            return size;
        }

        public int getRetainedSize() {
            if (position < 0 && position != PLACEHOLDER_POSITION) {
                return ESTIMATED_INDEX_SIZE_MULTIPLIER * size + ESTIMATED_INDEX_SIZE_EXTRA_BYTES;
            }
            return size;
        }
    }
}

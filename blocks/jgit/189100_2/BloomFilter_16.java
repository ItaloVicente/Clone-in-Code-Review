
package org.eclipse.jgit.lib;

import org.eclipse.jgit.annotations.NonNull;

public interface BloomFilter {

    boolean contains(Key key);

    class Key {
        private final int[] hashes;

        public Key(@NonNull int[] hashes) {
            this.hashes = hashes;
        }

        public int[] getHashes() {
            return hashes;
        }
    }
}

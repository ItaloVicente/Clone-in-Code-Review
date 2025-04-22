            if (entry == null) {
                if (++elementCount > threshold) {
                    rehash();
                    index = (hashCode(key) & 0x7FFFFFFF) % elementData.length;
                }
                if (index < firstSlot) {

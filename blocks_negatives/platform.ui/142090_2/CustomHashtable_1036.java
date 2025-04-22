        firstSlot = length;
        lastSlot = -1;
        HashMapEntry[] newData = new HashMapEntry[length];
        for (int i = elementData.length; --i >= 0;) {
            HashMapEntry entry = elementData[i];
            while (entry != null) {
                int index = (hashCode(entry.key) & 0x7FFFFFFF) % length;
                if (index < firstSlot) {

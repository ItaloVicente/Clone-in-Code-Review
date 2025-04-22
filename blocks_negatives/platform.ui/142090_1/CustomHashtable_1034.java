                entry = new HashMapEntry(key, value);
                entry.next = elementData[index];
                elementData[index] = entry;
                return null;
            }
            Object result = entry.value;
            entry.value = value;
            return result;

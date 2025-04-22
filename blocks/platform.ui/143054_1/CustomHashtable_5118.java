				entry = new HashMapEntry(key, value);
				entry.next = elementData[index];
				elementData[index] = entry;
				return null;
			}
			Object result = entry.value;
			entry.key = key; // important to avoid hanging onto keys that are equal but "old" -- see bug 30607
			entry.value = value;
			return result;

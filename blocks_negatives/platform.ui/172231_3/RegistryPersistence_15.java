			if (currentCount >= indexedArray[index].length) {
				final IConfigurationElement[] copy = new IConfigurationElement[indexedArray[index].length * 2];
				System.arraycopy(indexedArray[index], 0, copy, 0, currentCount);
				elements = copy;
				indexedArray[index] = elements;
			} else {
				elements = indexedArray[index];
			}

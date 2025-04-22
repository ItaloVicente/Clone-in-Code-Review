				Object[] elements = (Object[]) inputElement;
				Object[] results = new Object[elements.length];
				System.arraycopy(elements, 0, results, 0, elements.length);
				Collections.sort(Arrays.asList(results), comparer);
				return results;
			}

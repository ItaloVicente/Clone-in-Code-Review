				if (!hasNext())
					throw new NoSuchElementException();

				Entry res = entry;
				entry = null;
				return res;

				@Override
				public PackWriter next() {
					if (hasNext()) {
						PackWriter result = next;
						next = null;
						return result;
					}
					throw new NoSuchElementException();
				}

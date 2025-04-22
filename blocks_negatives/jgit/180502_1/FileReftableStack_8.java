				if (last != null) {
					if (last.maxUpdateIndex() >= t.minUpdateIndex()) {
						throw new ReftableNumbersNotIncreasingException(name,
								last.maxUpdateIndex(), t.minUpdateIndex());
					}
				}
				last = t;


						if (entry != null) {
							entries[providerIndex].add(entry);
							count++;
							countTotal++;
							if (providerIndex == 0 && entry.element == perfectMatch) {
								perfectMatchAdded = true;
								maxCount = MAX_COUNT_TOTAL;

						ListDiffEntry[] entries = new ListDiffEntry[] {
								Diffs.createListDiffEntry(subListIndex, false,
										oldElement),
								Diffs.createListDiffEntry(subListIndex, true,
										newElement) };
						ListDiff diff = Diffs.createListDiff(entries);

				while (!source.isEmpty() && newCurrent == null) {
					NavigationHistoryEntry entry = (NavigationHistoryEntry) source.removeFirst();
					if (entry.equals(target)) {
						newCurrent = entry;
					} else {
						destination.addFirst(entry);
					}
				}
				Assert.isTrue(newCurrent != null);
				perTabHistory.currentEntry = newCurrent;
				try {
					ignoreEntries++;
					if (newCurrent.editorInfo.memento != null) {
						newCurrent.editorInfo.restoreEditor();
						checkDuplicates(newCurrent.editorInfo);
					}
					newCurrent.restoreLocation();
					updateActions();
				} finally {
					ignoreEntries--;
				}
			}
		}
	}

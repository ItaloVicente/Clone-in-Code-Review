		if (!isBare()) {
			File indexFile = getIndexFile();
			if (save != null) {
				if (save.isModified(indexFile)) {
					System.out.println("Index changed: " + indexFile);
					fireEvent(new IndexChangedEvent());
				} else
					System.out.println("Index NOT hanged: " + indexFile);
			} else {
				save = FileSnapshot.save(getIndexFile());
				System.out.println("Snapshotting index file: " + indexFile);
			}
		}


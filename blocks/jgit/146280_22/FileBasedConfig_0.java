			final FileSnapshot newSnapshot;
			if (useFileSnapshotWithConfig) {
				newSnapshot = FileSnapshot.save(getFile());
			} else {
				newSnapshot = FileSnapshot.saveNoConfig(getFile());
			}

		if (files == null) {
			throw new IllegalStateException();
		}

		FileSnapshot o = FileSnapshot.save(files.ref);
		FileSnapshot n = FileSnapshot.save(files.lck);

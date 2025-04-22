			}
		}

		private void add(StagingEntry entry, Collection<String> files) {
			File file = entry.getLocation().toFile();
			if (file.exists()) {
				files.add(file.getAbsolutePath());

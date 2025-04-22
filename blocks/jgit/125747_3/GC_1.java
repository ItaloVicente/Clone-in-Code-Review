	private void deleteEmptyRefsFolders() throws IOException {
		try (Stream<Path> entries = Files.list(refs)) {
			Iterator<Path> iterator = entries.iterator();
			while (iterator.hasNext()) {
				try (Stream<Path> s = Files.list(iterator.next())) {
					s.forEach(this::deleteDir);
				}
			}
		}
	}

	private void deleteDir(Path dir) {
		try (Stream<Path> dirs = Files.walk(dir)) {
			dirs.filter(this::isDirectory).sorted(Comparator.reverseOrder())
					.forEach(this::delete);
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

	private boolean isDirectory(Path p) {
		return p.toFile().isDirectory();
	}

	private boolean delete(Path d) {
		try {
			Instant threshold = Instant.now().minus(30
			Instant lastModified = Files.getLastModifiedTime(d).toInstant();
			if (lastModified.isBefore(threshold)) {
				return d.toFile().delete();
			}
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
		return false;
	}


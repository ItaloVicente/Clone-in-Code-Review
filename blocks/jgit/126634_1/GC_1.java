	private void deleteEmptyRefsFolders() throws IOException {
		Path refs = repo.getDirectory().toPath().resolve(Constants.R_REFS);
		Instant threshold = Instant.now().minus(30
		try (Stream<Path> entries = Files.list(refs)) {
			Iterator<Path> iterator = entries.iterator();
			while (iterator.hasNext()) {
				try (Stream<Path> s = Files.list(iterator.next())) {
					s.filter(path -> canBeSafelyDeleted(path
				}
			}
		}
	}

	private boolean canBeSafelyDeleted(Path path
		try {
			return Files.getLastModifiedTime(path).toInstant().isBefore(threshold);
		}
		catch (IOException e) {
			LOG.warn(MessageFormat.format(
					JGitText.get().cannotAccessLastModifiedForSafeDeletion
					path)
			return false;
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

	private void delete(Path d) {
		try {
			Files.delete(d);
		} catch (IOException e) {
			LOG.error(MessageFormat.format(JGitText.get().cannotDeleteFile
					e);
		}
	}


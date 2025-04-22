	private void deleteEmptyObjectsDirs() {
		Path objectsDir = Paths
				.get(repo.getObjectsDirectory().getAbsolutePath());
		try (DirectoryStream<Path> dirs = Files.newDirectoryStream(objectsDir
				Files::isDirectory)) {
			dirs.forEach(this::deleteDir);
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}

	private void deleteDir(Path dir) {
		try (Stream<Path> entries = Files.list(dir)) {
			if (dir.getFileName().toString().length() == 2
					&& entries.count() == 0) {
				Files.deleteIfExists(dir);
			}
		} catch (DirectoryNotEmptyException ignored) {
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
	}


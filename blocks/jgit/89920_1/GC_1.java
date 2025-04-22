	private void deleteOrphans() {
		Path packDir = Paths.get(repo.getObjectsDirectory().getAbsolutePath()
		List<String> fileNames = null;
		try (Stream<Path> files = Files.list(packDir)) {
			fileNames = files.map(path -> path.getFileName().toString())
					.filter(name -> {
						return (name.endsWith(PACK_EXT)
								|| name.endsWith(BITMAP_EXT)
								|| name.endsWith(INDEX_EXT));
					}).sorted(Collections.reverseOrder())
					.collect(Collectors.toList());
		} catch (IOException e1) {
		}
		if (fileNames == null) {
			return;
		}

		String base = null;
		for (String n : fileNames) {
			if (n.endsWith(PACK_EXT)) {
				base = n.substring(0
			} else {
				if (base == null || !n.startsWith(base)) {
					try {
						Files.delete(new File(packDir.toFile()
					} catch (IOException e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}
	}


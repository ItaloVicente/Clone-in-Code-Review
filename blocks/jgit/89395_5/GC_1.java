	private void deleteOrphans() {
		Path packDir = Paths.get(repo.getObjectsDirectory().getAbsolutePath()
		String base = null;

		String[] list = packDir.toFile().list((file
		});
		Arrays.sort(list);
		for (int i = list.length - 1; i >= 0; i--) {
			if (list[i].endsWith(PackExt.PACK.getExtension())) {
				base = list[i].substring(0
			} else {
				if (base == null || !list[i].startsWith(base)) {
					try {
						Files.delete(
								new File(packDir.toFile()
					} catch (IOException e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}

	}


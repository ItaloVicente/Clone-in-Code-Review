	private void deleteOrphans() {
		Path packDir = Paths.get(repo.getObjectsDirectory().getAbsolutePath()

		String[] list = packDir.toFile().list((file
		});
		if (list == null) {
			return;
		}
		Arrays.sort(list);

		String base = null;
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


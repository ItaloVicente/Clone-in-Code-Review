			if (n.endsWith(PACK_EXT) || n.endsWith(KEEP_EXT)) {
				base = n.substring(0, n.lastIndexOf('.'));
			} else {
				if (base == null || !n.startsWith(base)) {
					try {
						Path delete = packDir.resolve(n);
						FileUtils.delete(delete.toFile(),
								FileUtils.RETRY | FileUtils.SKIP_MISSING);
						LOG.warn(JGitText.get().deletedOrphanInPackDir, delete);
					} catch (IOException e) {
						LOG.error(e.getMessage(), e);
					}

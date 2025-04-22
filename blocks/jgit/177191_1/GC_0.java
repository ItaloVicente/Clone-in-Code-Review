			PackFile pf = new PackFile(packDir.toFile()
			PackExt ext = pf.getPackExt();
			if (ext.equals(PACK) || ext.equals(KEEP)) {
				latestId = pf.getId();
			}
			if (latestId == null || !pf.getId().equals(latestId)) {
				try {
					FileUtils.delete(pf
							FileUtils.RETRY | FileUtils.SKIP_MISSING);
					LOG.warn(JGitText.get().deletedOrphanInPackDir
				} catch (IOException e) {
					LOG.error(e.getMessage()

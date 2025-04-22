			try {
				FileUtils.delete(rootDir,
						FileUtils.RECURSIVE | FileUtils.RETRY);
			} catch (DirectoryNotEmptyException e) {
				System.err.println(e.toString());
				listDirectory(rootDir, true);
				throw e;
			}

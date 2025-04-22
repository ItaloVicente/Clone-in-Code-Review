
			String[] files = repo.getWorkTree().list();
			boolean isWorkingDirEmpty = files != null && files.length == 0;
			if (deleteWorkDir && isWorkingDirEmpty) {
				FileUtils.delete(repo.getWorkTree(), FileUtils.RETRY | FileUtils.SKIP_MISSING);
			}

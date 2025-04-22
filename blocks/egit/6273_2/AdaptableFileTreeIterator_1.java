	private boolean findProjectFast(File currentFile, File ws) {
		File check = currentFile;

		if(currentFile.isFile()) {
			check = currentFile.getParentFile();
		}

		while(check != null && !ws.equals(check)) {
			if(new File(check, ".project").exists()) //$NON-NLS-1$
				return true;

			check = check.getParentFile();
		}

		return false;
	}


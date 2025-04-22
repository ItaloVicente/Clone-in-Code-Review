
	private static File newFolder(File root
		String subFolder = String.join("/"
		File result = new File(root
		if (!result.mkdirs()) {
			throw new IOException("Couldn't create folders " + root);
		}
		return result;
	}

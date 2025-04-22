		File resourceLocation = resource.getLocation().toFile();
		String resLocationAbsolutePath = resourceLocation.getAbsolutePath();

		for (Map.Entry<String, ObjectId> entry : updated.entrySet()) {
			String entryName = entry.getKey();
			File file = new File(workDir, entryName);

			if (file.getAbsolutePath().startsWith(resLocationAbsolutePath))
				return new GitFolderResourceVariant(resource);
		}

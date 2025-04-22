		String[] fileNameAndExtension = splitBaseFileNameAndExtension(
				new File(path));
		tempFile = File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_", //$NON-NLS-1$ //$NON-NLS-2$
				fileNameAndExtension[1],
				workingDir);
		copyFromStream();
		return tempFile;

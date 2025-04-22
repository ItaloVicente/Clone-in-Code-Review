		String[] fileNameAndExtension = splitBaseFileNameAndExtension(
				new File(path));
		tempFile = File.createTempFile(
				fileNameAndExtension[0] + "_" + midName + "_"
				fileNameAndExtension[1]
				directory);
		copyFromStream();
		return tempFile;

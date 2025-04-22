		if (actualLength < length) {
			String[] tempFileNames = fileNames;
			fileNames = new String[actualLength];
			System.arraycopy(tempFileNames, 0, fileNames, 0, actualLength);
		}
		event.data = fileNames;
	}

	@Override

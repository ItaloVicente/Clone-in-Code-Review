	protected IPath getDataLocation(IPath workspaceRoot) {
		return workspaceRoot.append(getDataLocationSuffix());
	}

	protected IPath getDataLocationSuffix() {
		return DATA_LOCATION_SUFFIX;
	}

	protected void createDirectoryHierarchy(IPath path) {
		File workspaceFile = new File(path.toOSString());
		workspaceFile.mkdirs();
	}

	protected void copyFile(String srcPath, String targetPath, String fileName) throws IOException {
		File workbenchModel = new File(srcPath, fileName);
		if (workbenchModel.exists()) {
			byte[] bytes = new byte[CHUNK_SIZE];
			FileInputStream inputStream = new FileInputStream(workbenchModel);
			FileOutputStream outputStream = new FileOutputStream(new File(targetPath, fileName));
			int read = inputStream.read(bytes, 0, CHUNK_SIZE);
			while (read != -1) {
				outputStream.write(bytes, 0, read);
				read = inputStream.read(bytes, 0, CHUNK_SIZE);
			}
			inputStream.close();
			outputStream.close();
		}
	}

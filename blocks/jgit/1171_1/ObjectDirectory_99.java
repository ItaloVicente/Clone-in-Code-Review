	long getObjectSize2(WindowCursor curs
			AnyObjectId objectId) throws IOException {
		try {
			File path = fileFor(objectName);
			FileInputStream in = new FileInputStream(path);
			try {
				return UnpackedObject.getSize(in
			} finally {
				in.close();
			}
		} catch (FileNotFoundException noFile) {
			return -1;
		}

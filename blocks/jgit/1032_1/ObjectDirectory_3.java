			File path = fileFor(objectName);
			FileInputStream in = new FileInputStream(path);
			try {
				return UnpackedObject.open(in
			} finally {
				in.close();
			}

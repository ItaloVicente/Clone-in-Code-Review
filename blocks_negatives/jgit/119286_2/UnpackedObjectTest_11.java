		{
			FileInputStream fs = new FileInputStream(path(id));
			try {
				ol = UnpackedObject.open(fs, path(id), id, wc);
			} finally {
				fs.close();
			}

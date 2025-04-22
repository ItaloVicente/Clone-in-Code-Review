			String dir;
			try {
				dir = file.getCanonicalPath();
			} catch (IOException e1) {
				dir = file.getAbsolutePath();
			}

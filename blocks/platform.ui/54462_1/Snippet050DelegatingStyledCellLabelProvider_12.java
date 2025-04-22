		public File[] getChildren(File element) {
			File file = element;
			if (file.isDirectory()) {
				File[] listFiles = file.listFiles();
				if (listFiles != null) {
					return listFiles;

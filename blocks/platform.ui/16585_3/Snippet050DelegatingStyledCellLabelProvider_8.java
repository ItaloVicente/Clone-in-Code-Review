		public Image getImage(File element) {
			File file = element;
			if (file.isDirectory()) {
				return IMAGE1;
			} else {
				return IMAGE2;

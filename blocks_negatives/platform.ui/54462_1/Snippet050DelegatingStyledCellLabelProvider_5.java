		public Image getImage(Object element) {
			if (element instanceof File) {
				File file= (File) element;
				if (file.isDirectory()) {
					return IMAGE1;
				} else {
					return IMAGE2;
				}

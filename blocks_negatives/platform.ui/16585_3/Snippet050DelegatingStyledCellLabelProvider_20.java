		public Object getParent(Object element) {
			if (element instanceof File) {
				File file= (File) element;
				return file.getParentFile();
			}
			return null;

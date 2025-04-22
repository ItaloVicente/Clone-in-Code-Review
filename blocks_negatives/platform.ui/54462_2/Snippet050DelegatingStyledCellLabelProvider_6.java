		public Object[] getChildren(Object element) {
			if (element instanceof File) {
				File file= (File) element;
				if (file.isDirectory()) {
					File[] listFiles= file.listFiles();
					if (listFiles != null) {
						return listFiles;
					}

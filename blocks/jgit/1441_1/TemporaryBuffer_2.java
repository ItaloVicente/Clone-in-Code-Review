		@Override
		public InputStream openInputStream() throws IOException {
			if (onDiskFile == null)
				return super.openInputStream();
			return new FileInputStream(onDiskFile);
		}


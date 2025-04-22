					isRegularFile,
					creationTime, lastModifiedTime);
			this.path = pPath;
		}

		@Override
		public long getLength() {
			if (length == -1) {
				try {
					length = Files.size(path);
				} catch (IOException e) {
					length = 0;
				}
			}
			return length;

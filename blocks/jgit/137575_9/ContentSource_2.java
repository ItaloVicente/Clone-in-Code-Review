
		@Override
		public void close() {
			reader.close();
		}

		@Override
		public boolean isWorkingTreeSource() {
			return false;
		}


		@Override
		public boolean close() {
			boolean closed = super.close();
			shell.close();
			return closed;
		}

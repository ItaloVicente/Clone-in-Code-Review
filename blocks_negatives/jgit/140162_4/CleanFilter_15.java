	public final static FilterCommandFactory FACTORY = new FilterCommandFactory() {

		@Override
		public FilterCommand create(Repository db, InputStream in,
				OutputStream out) throws IOException {
			return new CleanFilter(db, in, out);
		}
	};

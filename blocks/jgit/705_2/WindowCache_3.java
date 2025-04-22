		long lastAccess;

		private boolean cleared;

		protected Ref(final PackFile pack
				final ByteWindow v
			super(v
			this.pack = pack;
			this.position = position;
			this.size = v.size();

	private static class FormatEntry {
		final Format<?> format;
		final int refcnt;

		public FormatEntry(Format<?> format
			if (format == null)
				throw new NullPointerException();
			this.format = format;
			this.refcnt = refcnt;
		}
	};


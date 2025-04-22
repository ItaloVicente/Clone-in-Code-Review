	private static final Comparator<Entry> ENTRY_CMP = new Comparator<Entry>() {
		@Override
		public int compare(Entry a, Entry b) {
			return Paths.compare(
					a.encodedName, 0, a.encodedNameLen, a.getMode().getBits(),
					b.encodedName, 0, b.encodedNameLen, b.getMode().getBits());
		}
	};


	private static void delete(final File d) {
		if (d.isDirectory()) {
			final File[] items = d.listFiles();
			if (items != null) {
				for (final File c : items)
					delete(c);
			}
		}
		d.delete();
	}

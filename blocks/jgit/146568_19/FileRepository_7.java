		if (ref == null) {
			return null;
		}
		if (refs instanceof FileReftableDatabase) {
			return ((FileReftableDatabase)refs).getReflogReader(ref.getName());
		}

		return new ReflogReaderImpl(this

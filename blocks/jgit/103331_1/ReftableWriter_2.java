		writeRef(ref
	}

	public void writeRef(Ref ref
		if (updateIndex < minUpdateIndex) {
			throw new IllegalArgumentException();
		}
		long d = updateIndex - minUpdateIndex;
		long blockPos = refs.write(new RefEntry(ref

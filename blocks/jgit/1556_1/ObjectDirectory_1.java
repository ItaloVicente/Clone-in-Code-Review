	@Override
	boolean insertUnpackedObject(File tmp
		if (has(id)) {
			tmp.delete();
			return true;
		}
		tmp.setReadOnly();

		final File dst = fileFor(id);
		if (tmp.renameTo(dst)) {
			unpackedObjectCache.add(id);
			return true;
		}

		dst.getParentFile().mkdir();
		if (tmp.renameTo(dst)) {
			unpackedObjectCache.add(id);
			return true;
		}

		if (has(id)) {
			tmp.delete();
			return true;
		}

		tmp.delete();
		return false;

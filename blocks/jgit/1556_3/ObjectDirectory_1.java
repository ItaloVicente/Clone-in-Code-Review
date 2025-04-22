	@Override
	boolean insertUnpackedObject(File tmp
		if (!force && has(id)) {
			tmp.delete();
			return true;
		}
		tmp.setReadOnly();

		final File dst = fileFor(id);
		if (force && dst.exists()) {
			tmp.delete();
			return true;
		}
		if (tmp.renameTo(dst)) {
			unpackedObjectCache.add(id);
			return true;
		}

		dst.getParentFile().mkdir();
		if (tmp.renameTo(dst)) {
			unpackedObjectCache.add(id);
			return true;
		}

		if (!force && has(id)) {
			tmp.delete();
			return true;
		}

		tmp.delete();
		return false;

			final byte[] data
		if (0 < maxObjectSizeLimit && maxObjectSizeLimit < size) {
			throw new IOException("Object " + id.name()
					+ " is too large. Use: \"git ls-tree -r HEAD | grep " + id.name()
					+ "\" to find out the path of the object in your Git repository");
		}

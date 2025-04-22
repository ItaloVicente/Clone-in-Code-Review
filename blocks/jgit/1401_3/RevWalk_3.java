	byte[] getCachedBytes(RevObject obj) throws LargeObjectException
			MissingObjectException
		return getCachedBytes(obj
	}

	byte[] getCachedBytes(RevObject obj
			throws LargeObjectException
		try {
			return ldr.getCachedBytes(bigFileThreshold);
		} catch (LargeObjectException tooBig) {
			tooBig.setObjectId(obj);
			throw tooBig;
		}
	}


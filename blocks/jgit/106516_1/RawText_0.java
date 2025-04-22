
	public static byte[] openText(ObjectLoader ldr
		long sz = ldr.getSize();
		if (sz <= FIRST_FEW_BYTES) {
			byte[] data = ldr.getBytes(threshold);
			if (RawText.isBinary(data)) {
				return null;
			}
			return data;
		}

		if (sz > threshold) {
			return null;
		}

		try (InputStream stream = ldr.openStream()) {
			if (RawText.isBinary(stream)) {
				return null;
			}
		}

		try {
			return ldr.getBytes(threshold);
		} catch (LargeObjectException.ExceedsLimit overLimit) {
			return null;
		} catch (LargeObjectException.ExceedsByteArrayLimit overLimit) {
			return null;
		} catch (LargeObjectException.OutOfMemory tooBig) {
			return null;
		}
	}

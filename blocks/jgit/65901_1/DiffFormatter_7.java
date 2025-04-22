
	private void writeGitBinaryPatch(ByteArrayOutputStream buf
			byte[] bRaw) throws IOException {
		emitBinaryDiffBody(buf
		emitBinaryDiffBody(buf
	}

	private void emitBinaryDiffBody(ByteArrayOutputStream buf
			byte[] bRaw) throws IOException {

		byte[] data;
		byte[] deflatedB = deflate(bRaw);
		byte[] deflatedDelta = null;
		byte[] delta = null;

		if (aRaw.length > 0 && bRaw.length > 0
		{
			delta = diff_delta(aRaw
			if (delta != null) {
				deflatedDelta = deflate(delta);
			}
		}

		if (deflatedDelta != null && delta != null
				&& deflatedDelta.length < deflatedB.length) {
			buf.write(encodeASCII(String.format("delta %s\n"
					Integer.valueOf(delta.length))));
			data = deflatedDelta;
		} else {
			buf.write(encodeASCII(String.format("literal %s\n"
					Integer.valueOf(bRaw.length))));
			data = deflatedB;
		}

		try (OutputStream outputStream = new GitBinaryPatchOutputStream(buf)) {
			outputStream.write(data);
		}
	}

	private byte[] diff_delta(byte[] src
			throws IOException {
		DeltaIndex deltaIndex = new DeltaIndex(src);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		boolean encoded = deltaIndex.encode(result
		return encoded ? result.toByteArray() : null;
	}

	private byte[] deflate(byte[] bRaw) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (OutputStream outputStream = new DeflaterOutputStream(baos)) {
			outputStream.write(bRaw);
		}
		return baos.toByteArray();
	}

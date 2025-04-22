	private int resolveDecodedLength(int lengthByte)
			throws PatchApplyException {
		if (lengthByte <= 122 && lengthByte >= 97) {
			return lengthByte - 70;
		} else if (lengthByte <= 90 && lengthByte >= 65) {
			return lengthByte - 64;
		} else {
			throw new PatchApplyException(
		}
	}

	private int calcTotalBinaryDecodedLength(byte[] buf
			throws PatchApplyException {
		int total = 0;
		while (buf[ptr] != '\n') {
			int sizeChar = buf[ptr++];
			total += resolveDecodedLength(sizeChar);
			ptr = nextLF(buf
		}
		return total;
	}

	private void applyBinaryPatch(File f
			throws PatchApplyException {
		if (fh.getChangeType() != ChangeType.ADD) {
			throw new PatchApplyException(
		}

		BinaryHunk forwardBinaryHunk = fh.getForwardBinaryHunk();
		byte[] buf = forwardBinaryHunk.getBuffer();
		int bufPos = forwardBinaryHunk.getStartOffset();

		int totalDecodedSize = calcTotalBinaryDecodedLength(buf
		byte[] allDecodedBytes = new byte[totalDecodedSize];
		int decPos = 0;

		bufPos = nextLF(buf
		while (buf[bufPos] != '\n') {
			int decodedLineLength = resolveDecodedLength(buf[bufPos++]);
			int nextLF = nextLF(buf
			byte[] decodedLine = decode85(buf
					decodedLineLength);
			System.arraycopy(decodedLine
					decodedLineLength);
			decPos += decodedLineLength;
			bufPos = nextLF;
		}

		byte[] inflated = new byte[0];
		try {
			inflated = inflate(allDecodedBytes
		} catch (DataFormatException dfe) {
			throw new PatchApplyException("Unable to inflate compressed data"
		}

		try (FileOutputStream fos = new FileOutputStream(f)) {
			fos.write(inflated);
		} catch (IOException ioe) {
			throw new PatchApplyException("Unable to write data to file"
		}
	}

	private void applyTextPatch(File f

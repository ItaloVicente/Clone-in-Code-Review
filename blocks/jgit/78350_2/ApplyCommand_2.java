	private int resolveDecodedLength(int lengthByte) throws PatchApplyException {
		if (lengthByte <= 122 && lengthByte >= 97) {
			return lengthByte - 70;
		} else if (lengthByte <= 90 && lengthByte >= 65) {
			return lengthByte - 64;
		} else {
		}
	}

	private int calcTotalBinaryDecodedLength(byte[] buf
		int total = 0;
		while (buf[ptr] != '\n') {
			int sizeChar = buf[ptr++];
			total += resolveDecodedLength(sizeChar);
			ptr = nextLF(buf
		}
		return total;
	}

	private void applyBinaryPatch(File f
		if (fh.getChangeType() != ChangeType.ADD) {
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
			System.arraycopy(decodedLine
			decPos += decodedLineLength;
			bufPos = nextLF;
		}

		byte[] inflated = inflate(allDecodedBytes

		try (FileOutputStream fos = new FileOutputStream(f)) {
			fos.write(inflated);
		} catch (IOException ioe) {
			throw new PatchApplyException("Unable to write data to file"
		}
	}

	private void applyTextPatch(File f

	void writeFileHeader(int version
		System.arraycopy(Constants.PACK_SIGNATURE
		NB.encodeInt32(headerBuffer
		NB.encodeInt32(headerBuffer
		write(headerBuffer
	}

	public void writeHeader(ObjectToPack otp
			throws IOException {
		if (otp.isDeltaRepresentation()) {
			if (ofsDelta) {
				ObjectToPack baseInPack = otp.getDeltaBase();
				if (baseInPack != null && baseInPack.isWritten()) {
					final long start = count;
					int n = encodeTypeSize(Constants.OBJ_OFS_DELTA
					write(headerBuffer

					long offsetDiff = start - baseInPack.getOffset();
					n = headerBuffer.length - 1;
					headerBuffer[n] = (byte) (offsetDiff & 0x7F);
					while ((offsetDiff >>= 7) > 0)
						headerBuffer[--n] = (byte) (0x80 | (--offsetDiff & 0x7F));
					write(headerBuffer
					return;
				}
			}

			int n = encodeTypeSize(Constants.OBJ_REF_DELTA
			otp.getDeltaBaseId().copyRawTo(headerBuffer
			write(headerBuffer
		} else {
			int n = encodeTypeSize(otp.getType()
			write(headerBuffer
		}
	}

	private int encodeTypeSize(int type
		long nextLength = rawLength >>> 4;
		headerBuffer[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00)
				| (type << 4) | (rawLength & 0x0F));
		rawLength = nextLength;
		int n = 1;
		while (rawLength > 0) {
			nextLength >>>= 7;
			headerBuffer[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
			rawLength = nextLength;
		}
		return n;
	}

	public byte[] getCopyBuffer() {
		if (copyBuffer == null)
			copyBuffer = new byte[16 * 1024];
		return copyBuffer;
	}


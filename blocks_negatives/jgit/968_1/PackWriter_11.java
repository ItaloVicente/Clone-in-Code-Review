	private void writeDeltaObjectHeader(final ObjectToPack otp,
			final PackedObjectLoader reuse) throws IOException {
		if (deltaBaseAsOffset && otp.getDeltaBase() != null) {
			writeObjectHeader(Constants.OBJ_OFS_DELTA, reuse.getRawSize());

			final ObjectToPack deltaBase = otp.getDeltaBase();
			long offsetDiff = otp.getOffset() - deltaBase.getOffset();
			int pos = buf.length - 1;
			buf[pos] = (byte) (offsetDiff & 0x7F);
			while ((offsetDiff >>= 7) > 0) {
				buf[--pos] = (byte) (0x80 | (--offsetDiff & 0x7F));
			}

			out.write(buf, pos, buf.length - pos);
		} else {
			writeObjectHeader(Constants.OBJ_REF_DELTA, reuse.getRawSize());
			otp.getDeltaBaseId().copyRawTo(buf, 0);
			out.write(buf, 0, Constants.OBJECT_ID_LENGTH);
		}
	}

	private void writeObjectHeader(final int objectType, long dataLength)
			throws IOException {
		long nextLength = dataLength >>> 4;
		int size = 0;
		buf[size++] = (byte) ((nextLength > 0 ? 0x80 : 0x00)
				| (objectType << 4) | (dataLength & 0x0F));
		dataLength = nextLength;
		while (dataLength > 0) {
			nextLength >>>= 7;
			buf[size++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (dataLength & 0x7F));
			dataLength = nextLength;
		}
		out.write(buf, 0, size);
	}


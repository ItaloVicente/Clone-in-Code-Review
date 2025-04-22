		if (otp.isDeltaRepresentation()) {
			if (packWriter.isDeltaBaseAsOffset()) {
				ObjectToPack baseInPack = otp.getDeltaBase();
				if (baseInPack != null && baseInPack.isWritten()) {
					final long start = count;
					int n = encodeTypeSize(Constants.OBJ_OFS_DELTA, rawLength);
					write(headerBuffer, 0, n);

					long offsetDiff = start - baseInPack.getOffset();
					n = headerBuffer.length - 1;
					headerBuffer[n] = (byte) (offsetDiff & 0x7F);
					while ((offsetDiff >>= 7) > 0)
						headerBuffer[--n] = (byte) (0x80 | (--offsetDiff & 0x7F));
					write(headerBuffer, n, headerBuffer.length - n);
					return;
				}
			}

			int n = encodeTypeSize(Constants.OBJ_REF_DELTA, rawLength);

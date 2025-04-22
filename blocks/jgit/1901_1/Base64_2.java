	public static byte[] decode(byte[] source
		int outBuffPosn = 0;

		byte[] b4 = new byte[4];
		int b4Posn = 0;

		for (int i = off; i < off + len; i++) {
			byte sbiCrop = (byte) (source[i] & 0x7f);
			byte sbiDecode = DEC[sbiCrop];

			if (EQUALS_SIGN_DEC <= sbiDecode) {
				b4[b4Posn++] = sbiCrop;
				if (b4Posn > 3) {
					outBuffPosn += decode4to3(b4
					b4Posn = 0;

					if (sbiCrop == EQUALS_SIGN)
						break;
				}

			} else if (sbiDecode != WHITE_SPACE_DEC)
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().badBase64InputCharacterAt
						source[i] & 0xff));
		}

		if (outBuff.length == outBuffPosn)
			return outBuff;

		byte[] out = new byte[outBuffPosn];
		System.arraycopy(outBuff
		return out;
	}

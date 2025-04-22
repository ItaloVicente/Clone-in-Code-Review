
		byte[] aRaw = getRawBytes(id1);
		byte[] bRaw = getRawBytes(id2);

		if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
			out.println("Binary files differ");
			return;
		}

		RawText a = getRawText(aRaw);
		RawText b = getRawText(bRaw);

		out.println("index " + id1.abbreviate(db, 7).name()
			+ ".." + id2.abbreviate(db, 7).name()
			+ (mode1.equals(mode2) ? " " + mode1 : ""));
		out.println("--- " + (isNew ?  "/dev/null" : name1));
		out.println("+++ " + (isDelete ?  "/dev/null" : name2));

		byte[] aRaw = getRawBytes(id1);
		byte[] bRaw = getRawBytes(id2);

		if (RawText.isBinary(aRaw) || RawText.isBinary(bRaw)) {
			out.println("Binary files differ");
			return;
		}

		RawText a = getRawText(aRaw);
		RawText b = getRawText(bRaw);
		MyersDiff diff = new MyersDiff(a, b);
		fmt.formatEdits(out, a, b, diff.getEdits());
	}

	private byte[] getRawBytes(ObjectId id) throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new byte[] {};
		return db.openBlob(id).getCachedBytes();
	}

	private RawText getRawText(byte[] raw) {
		if (ignoreWsAll)
			return new RawTextIgnoreAllWhitespace(raw);
		else if (ignoreWsTrailing)
			return new RawTextIgnoreTrailingWhitespace(raw);
		else if (ignoreWsChange)
			return new RawTextIgnoreWhitespaceChange(raw);
		else if (ignoreWsLeading)
			return new RawTextIgnoreLeadingWhitespace(raw);
		else
			return new RawText(raw);

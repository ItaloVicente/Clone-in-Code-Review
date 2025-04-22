			return new RawText(new byte[] {});
		byte[] raw = db.openBlob(id).getCachedBytes();
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

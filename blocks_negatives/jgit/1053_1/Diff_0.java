			new BufferedOutputStream(System.out)) {
		@Override
		protected RawText newRawText(byte[] raw) {
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
		}
	};

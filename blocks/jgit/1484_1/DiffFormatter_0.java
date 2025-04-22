		return createFormatResult(ent).header;
	}

	private static class FormatResult {
		FileHeader header;

		RawText a;

		RawText b;
	}

	private FormatResult createFormatResult(DiffEntry ent) throws IOException
			CorruptObjectException
		final FormatResult res = new FormatResult();

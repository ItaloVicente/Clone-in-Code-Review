		StringBuilder sb = new StringBuilder(text);
		List<WrapEdit> wrapEdits = SpellcheckableMessageArea
				.calculateWrapEdits(text,
						SpellcheckableMessageArea.MAX_LINE_WIDTH, lineDelimiter);
		for (WrapEdit wrapEdit : wrapEdits) {
			sb.replace(wrapEdit.getStart(),
					wrapEdit.getStart() + wrapEdit.getLength(), wrapEdit.getReplacement());
		}
		return sb.toString();

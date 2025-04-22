		String text = getNormalizedText(node.getNodeValue());
		if (!whitespaceNormalized)
			return text;
		if (text.length() > 0 && node.getPreviousSibling() == null && isIgnorableWhiteSpace(text.substring(0, 1), true))
			return text.substring(1);
		if (text.length() > 1 && node.getNextSibling() == null
				&& isIgnorableWhiteSpace(text.substring(text.length() - 1), true))
			return text.substring(0, text.length() - 1);
		return text;

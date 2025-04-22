	private String processAmpersandEscapes(String pTaggedText) {
		try {
			String taggedText = pTaggedText.replaceAll("&quot;", "&#034;"); //$NON-NLS-1$//$NON-NLS-2$
			taggedText = taggedText.replaceAll("&apos;", "&#039;"); //$NON-NLS-1$//$NON-NLS-2$
			taggedText = taggedText.replaceAll("&lt;", "&#060;"); //$NON-NLS-1$//$NON-NLS-2$
			taggedText = taggedText.replaceAll("&gt;", "&#062;"); //$NON-NLS-1$//$NON-NLS-2$
			taggedText = taggedText.replaceAll("&amp;", "&#038;"); //$NON-NLS-1$//$NON-NLS-2$
			taggedText = taggedText.replaceAll("&([^#])", "&#038;$1"); //$NON-NLS-1$//$NON-NLS-2$
			return taggedText;
		} catch (Exception e) {
			return pTaggedText;
		}
	}


			attributes.clear();
			if (value != null) {
				for (String string : value) {
					attributes.put(TAG_VALUE, string == null ? "" : string); //$NON-NLS-1$
					out.printTag(TAG_ITEM, attributes, true);
				}
			}
			out.endTag(TAG_LIST);
			attributes.clear();
		}
		for (IDialogSettings iDialogSettings : sections.values()) {
			((DialogSettings) iDialogSettings).save(out);
		}
		out.endTag(TAG_SECTION);
	}

	private static class XMLWriter extends BufferedWriter {

		protected int tab;

		protected static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //$NON-NLS-1$

		public XMLWriter(OutputStream output) throws IOException {

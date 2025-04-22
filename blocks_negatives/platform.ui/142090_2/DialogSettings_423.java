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

    /**
     * A simple XML writer.  Using this instead of the javax.xml.transform classes allows
     * compilation against JCL Foundation (bug 80059).
     */
    private static class XMLWriter extends BufferedWriter {

    	/** current number of tabs to use for indent */
    	protected int tab;

    	/** the xml header */

    	/**
    	 * Create a new XMLWriter
    	 * @param output the stream to write the output to
    	 * @throws IOException
    	 */
    	public XMLWriter(OutputStream output) throws IOException {

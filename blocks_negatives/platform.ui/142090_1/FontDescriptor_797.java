    /**
     * <p>Returns a FontDescriptor that is equivalent to the receiver, but
     * has the given style bits, in addition to any styles the receiver already has.</p>
     *
     * <p>Does not modify the receiver.</p>
     *
     * @param style a bitwise combination of SWT.NORMAL, SWT.ITALIC and SWT.BOLD
     * @return a new FontDescriptor with the given additional style bits
     * @since 3.3
     */
    public final FontDescriptor withStyle(int style) {
    	FontData[] data = getFontData();

    /**
     * <p>Returns a FontDescriptor that is equivalent to the reciever, but
     * has the given style bits, in addition to any styles the reciever already has.</p>
     *
     * <p>Does not modify the reciever.</p>
     *
     * @param style a bitwise combination of SWT.NORMAL, SWT.ITALIC and SWT.BOLD
     * @return a new FontDescriptor with the given additional style bits
     * @since 3.3
     */
    public final FontDescriptor withStyle(int style) {
    	FontData[] data = getFontData();

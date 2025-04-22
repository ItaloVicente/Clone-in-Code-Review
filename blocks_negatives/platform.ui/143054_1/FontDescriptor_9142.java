     * Returns a FontDescriptor that is equivalent to the reciever, but uses
     * the given style bits.
     *
     * <p>Does not modify the reciever.</p>
     *
     * @param style a bitwise combination of SWT.NORMAL, SWT.ITALIC and SWT.BOLD
     * @return a new FontDescriptor with the given style
     *
     * @since 3.3
     */
    public final FontDescriptor setStyle(int style) {
    	FontData[] data = getFontData();

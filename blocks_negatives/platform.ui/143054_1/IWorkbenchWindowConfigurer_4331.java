    /**
     * Sets the style bits to use for the window's shell when it is created.
     * This method has no effect after the shell is created.
     * That is, it must be called within the <code>preWindowOpen</code>
     * callback on <code>WorkbenchAdvisor</code>.
     * <p>
     * For more details on the applicable shell style bits, see the
     * documentation for {@link org.eclipse.swt.widgets.Shell}.
     * </p>
     *
     * @param shellStyle the shell style bits
     */
    void setShellStyle(int shellStyle);

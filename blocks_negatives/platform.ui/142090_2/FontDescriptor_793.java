    /**
     * Creates a new FontDescriptor given an OS-specific font name, height, and style.
     *
     * @see Font#Font(org.eclipse.swt.graphics.Device, java.lang.String, int, int)
     *
     * @param name os-specific font name
     * @param height height (pixels)
     * @param style a bitwise combination of NORMAL, BOLD, ITALIC
     * @return a new FontDescriptor
     */
    public static FontDescriptor createFrom(String name, int height, int style) {
        return createFrom(new FontData(name, height, style));
    }

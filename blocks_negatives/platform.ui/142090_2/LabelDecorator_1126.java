    /**
     * Prepare the element for decoration. If it is already decorated and ready for update
     * return true. If decoration is pending return false.
     * @param element The element to be decorated
     * @param originalText The starting text.
     * @param context The decoration context
     * @return boolean <code>true</code> if the decoration is ready for this element
     */
    public abstract boolean prepareDecoration(Object element, String originalText, IDecorationContext context);

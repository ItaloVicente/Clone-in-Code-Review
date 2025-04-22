    /**
     * Return the default system in-place editor part
     * or <code>null</code> if not support by platform.
     */
    public static IEditorPart getSystemInPlaceEditor() {
        if (inPlaceEditorSupported()) {
            return getOleEditor();
        }
        return null;
    }

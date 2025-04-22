    /**
     * The <code>PasteAction</code> implementation of this
     * <code>SelectionListenerAction</code> method enables this action if
     * a resource compatible with what is on the clipboard is selected.
     *
     * -Clipboard must have IResource or java.io.File
     * -Projects can always be pasted if they are open
     * -Workspace folder may not be copied into itself
     * -Files and folders may be pasted to a single selected folder in open
     * 	project or multiple selected files in the same folder
     */
    @Override

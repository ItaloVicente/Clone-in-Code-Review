    }

    /**
     * Creates a new action.
     *
     * @param shell the shell for any dialogs
     * @param clipboard a platform clipboard
     * @param pasteAction a paste action
     *
     * @since 2.0
     */
    public CopyAction(Shell shell, Clipboard clipboard, PasteAction pasteAction) {
        this(shell, clipboard);
        this.pasteAction = pasteAction;
    }

    /**
     * The <code>CopyAction</code> implementation of this method defined
     * on <code>IAction</code> copies the selected resources to the
     * clipboard.
     */
    @Override

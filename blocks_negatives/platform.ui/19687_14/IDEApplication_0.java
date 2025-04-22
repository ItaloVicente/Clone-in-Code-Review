        String title = IDEWorkbenchMessages.IDEApplication_versionTitle;
        String message = NLS.bind(IDEWorkbenchMessages.IDEApplication_versionMessage, url.getFile());

        MessageBox mbox = new MessageBox(shell, SWT.OK | SWT.CANCEL
                | SWT.ICON_WARNING | SWT.APPLICATION_MODAL);
        mbox.setText(title);
        mbox.setMessage(message);
        return mbox.open() == SWT.OK;

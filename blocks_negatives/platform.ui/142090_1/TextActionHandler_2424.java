        textControl.removeListener(SWT.Activate, textControlListener);
        textControl.removeListener(SWT.Deactivate, textControlListener);

        textControl.removeMouseListener(mouseAdapter);
        textControl.removeKeyListener(keyAdapter);

        activeTextControl = null;
        updateActionsEnableState();
    }

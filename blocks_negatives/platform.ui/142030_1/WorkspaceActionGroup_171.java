        if (event.keyCode == SWT.F5 && event.stateMask == 0) {
            if (refreshAction.isEnabled()) {
                refreshAction.refreshAll();
            }

            event.doit = false;
        }
    }

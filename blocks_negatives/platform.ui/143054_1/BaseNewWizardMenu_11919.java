        if (workbenchWindow != null) {
            super.dispose();
            unregisterListeners();
            showDlgAction.dispose();
            showDlgAction = null;
            workbenchWindow = null;
        }
    }

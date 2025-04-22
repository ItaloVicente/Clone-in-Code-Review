        if (workbenchWindow == null) {
            return;
        }
        workbenchWindow.getPartService().removePartListener(this);
        workbenchWindow = null;
    }

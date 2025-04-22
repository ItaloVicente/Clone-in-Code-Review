        if (workbenchWindow == null) {
            return;
        }
        workbenchWindow.removePageListener(this);
        workbenchWindow.getPartService().removePartListener(this);
        workbenchWindow = null;
    }

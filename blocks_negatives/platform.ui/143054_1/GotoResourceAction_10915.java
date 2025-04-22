        IContainer container = (IContainer) getViewer().getInput();
        GotoResourceDialog dialog = new GotoResourceDialog(getShell(),
                container, IResource.FILE | IResource.FOLDER
                        | IResource.PROJECT);
        dialog.open();
        Object[] result = dialog.getResult();
        if (result == null || result.length == 0
                || result[0] instanceof IResource == false) {

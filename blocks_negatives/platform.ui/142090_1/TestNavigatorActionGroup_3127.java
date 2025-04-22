    protected void makeActions() {
        Shell shell = navigator.getSite().getShell();
        addBookmarkAction = new AddBookmarkAction(navigator.getSite(), true);
        propertyDialogAction = new PropertyDialogAction(shell, navigator
                .getViewer());
    }

        IWorkbenchPage page = getView().getSite().getPage();
        for (Iterator i = getStructuredSelection().iterator(); i.hasNext();) {
            IMarker marker = (IMarker) i.next();
            try {
                IDE.openEditor(page, marker, OpenStrategy.activateOnOpen());
            } catch (PartInitException e) {

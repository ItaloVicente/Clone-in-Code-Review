	        ISelection selection = HandlerUtil.getCurrentSelection(event);
	        IStructuredSelection selectionToPass = StructuredSelection.EMPTY;
	        if (selection instanceof IStructuredSelection) {
	            selectionToPass = (IStructuredSelection) selection;
	        } else {
	            Class resourceClass = LegacyResourceSupport.getResourceClass();
	            if (resourceClass != null) {
	            	IWorkbenchWindow activeWorkbenchWindow = HandlerUtil.getActiveWorkbenchWindow(event);
	                IWorkbenchPart part = activeWorkbenchWindow.getPartService()
	                        .getActivePart();
	                if (part instanceof IEditorPart) {
	                    IEditorInput input = ((IEditorPart) part).getEditorInput();

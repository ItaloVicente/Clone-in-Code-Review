			if (promptForName) {
				BookmarkPropertiesDialog dialog= new BookmarkPropertiesDialog(shellProvider.getShell());
				dialog.setResource(resource);
				dialog.open();
			} else {
				Map<String, String> attrs = new HashMap<>();
				attrs.put(IMarker.MESSAGE, resource.getName());
				CreateMarkersOperation op= new CreateMarkersOperation(IMarker.BOOKMARK, attrs, resource, BookmarkMessages.CreateBookmark_undoText);
				try {
					PlatformUI.getWorkbench().getOperationSupport().getOperationHistory().execute(op, null, WorkspaceUndoUtil.getUIInfoAdapter(shellProvider.getShell()));
				} catch (ExecutionException e) {
					IDEWorkbenchPlugin.log(null, e); // We don't care
				}

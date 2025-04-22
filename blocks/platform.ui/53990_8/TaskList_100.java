        viewer.getControl().addHelpListener(e -> {
		    String contextId = null;
		    IMarker marker = (IMarker) ((IStructuredSelection) getSelection())
		            .getFirstElement();
		    if (marker != null) {
		        contextId = IDE.getMarkerHelpRegistry().getHelp(marker);
		    }

		    if (contextId == null) {
				contextId = ITaskListHelpContextIds.TASK_LIST_VIEW;
			}

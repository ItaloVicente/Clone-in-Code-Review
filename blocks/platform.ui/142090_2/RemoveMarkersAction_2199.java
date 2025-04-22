		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			IMarker[] markers = root.findMarkers(AddMarkersAction.CATEGORY_TEST_MARKER, false, IResource.DEPTH_ZERO);

			for (IMarker marker : markers) {
				String message = (String) marker.getAttribute(IMarker.MESSAGE);

				if (message != null && message.startsWith("this is a test")) {
					marker.delete();
				}
			}
		} catch (CoreException e) {
			openError(e);
		}
	}

	private void openError(Exception e) {
		String msg = e.getMessage();
		if (msg == null) {
			msg = e.getClass().getName();
		}

		e.printStackTrace();

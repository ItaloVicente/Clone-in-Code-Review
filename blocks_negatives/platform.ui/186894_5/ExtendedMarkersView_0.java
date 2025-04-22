			if (targetPartId == null) {
				return false;
			}
			if (WorkbenchPlugin.getDefault().getViewRegistry().find(targetPartId) == null) {
				return false;
			}
			ISelection selection = new StructuredSelection(marker.getResource());

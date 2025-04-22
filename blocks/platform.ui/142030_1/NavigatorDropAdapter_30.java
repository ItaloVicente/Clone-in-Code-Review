		String genericTitle = ResourceNavigatorMessages.DropAdapter_title;
		int codes = IStatus.ERROR | IStatus.WARNING;

		if (!status.isMultiStatus()) {
			ErrorDialog.openError(getShell(), genericTitle, null, status, codes);
			return;
		}

		IStatus[] children = status.getChildren();
		if (children.length == 1) {
			ErrorDialog.openError(getShell(), status.getMessage(), null, children[0], codes);
			return;
		}
		ErrorDialog.openError(getShell(), genericTitle, null, status, codes);
	}

	@Override

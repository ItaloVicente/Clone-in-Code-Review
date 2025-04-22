		IStatus[] children = status.getChildren();
		if (children.length == 1) {
			ErrorDialog.openError(getShell(), status.getMessage(), null, children[0], codes);
			return;
		}
		ErrorDialog.openError(getShell(), genericTitle, null, status, codes);
	}

	@Override

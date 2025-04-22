		if (currentSelection instanceof IStructuredSelection) {
			element = ((IStructuredSelection) currentSelection)
					.getFirstElement();
		} else {
			return null;
		}

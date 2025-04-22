		StructuredSelection ssel = convertSelection(selection);
		if (!ssel.isEmpty()) {
			getViewer().getControl().setRedraw(false);
			getViewer().setSelection(ssel, true);
			getViewer().getControl().setRedraw(true);
		}
	}

	@Override

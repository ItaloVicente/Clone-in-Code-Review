	}

	public void selectReveal(ISelection selection) {
		StructuredSelection ssel = convertSelection(selection);
		if (!ssel.isEmpty()) {
			getViewer().setSelection(ssel, true);
		}
	}

	@Override

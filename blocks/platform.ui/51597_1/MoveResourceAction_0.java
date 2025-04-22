    protected boolean updateSelection(IStructuredSelection selection) {
		moveProjectAction.selectionChanged(getStructuredSelection());

		return moveProjectAction.isEnabled() || super.updateSelection(selection);
    }

	@Override

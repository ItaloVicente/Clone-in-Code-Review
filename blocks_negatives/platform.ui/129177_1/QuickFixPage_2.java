
	}

	/**
	 * Return the marker resolution that is currently selected/
	 *
	 * @return IMarkerResolution or <code>null</code> if there is no
	 *         selection.
	 */
	private IMarkerResolution getSelectedResolution() {
		ISelection selection = resolutionsList.getSelection();
		if (!(selection instanceof IStructuredSelection)) {
			return null;
		}

		Object first = ((IStructuredSelection) selection).getFirstElement();

		return (IMarkerResolution) first;


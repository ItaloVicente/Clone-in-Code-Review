		fFilters.add(filter);
	}

	public void setValidator(ISelectionStatusValidator validator) {
		fValidator = validator;
	}

	public void setInput(Object input) {
		fInput = input;
	}

	public void setExpandedElements(Object[] elements) {
		fExpandedElements = elements;
	}

	public void setSize(int width, int height) {
		fWidth = width;
		fHeight = height;
	}

	protected void updateOKStatus() {
		if (!fIsEmpty) {
			if (fValidator != null) {
				fCurrStatus = fValidator.validate(fViewer.getCheckedElements());
				updateStatus(fCurrStatus);
			} else if (!fCurrStatus.isOK()) {
				fCurrStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID, IStatus.OK, "", //$NON-NLS-1$
						null);
			}
		} else {
			fCurrStatus = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID, IStatus.OK, fEmptyListMessage, null);
		}
		updateStatus(fCurrStatus);
	}

	@Override

		setResult(chosenContainerPathList);
		super.okPressed();
	}

	public void setValidator(ISelectionValidator validator) {
		this.validator = validator;
	}

	public void showClosedProjects(boolean show) {
		this.showClosedProjects = show;
	}

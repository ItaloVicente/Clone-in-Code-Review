
		if (newProjectWizard.getSelection()) {
			setPageComplete(true);
			return;
		}

		IStructuredSelection sel = (IStructuredSelection) tv.getSelection();

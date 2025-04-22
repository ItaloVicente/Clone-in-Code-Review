		group = new ContainerSelectionGroup(area, listener, allowNewContainerName, getMessage(), showClosedProjects);
		if (initialSelection != null) {
			group.setSelectedContainer(initialSelection);
		}

		statusMessage = new Label(area, SWT.WRAP);
		statusMessage.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		statusMessage.setText(" \n "); //$NON-NLS-1$
		statusMessage.setFont(parent.getFont());

		return dialogArea;
	}

	@Override

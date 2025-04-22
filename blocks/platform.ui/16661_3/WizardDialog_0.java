		if (wizard.needsProgressMonitor()) {
			GridLayout layout = new GridLayout();
			layout.marginHeight = 0;
			progressMonitorPart= createProgressMonitorPart(composite, layout);
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			progressMonitorPart.setLayoutData(gridData);
			progressMonitorPart.setVisible(false);
			applyDialogFont(progressMonitorPart);
		}

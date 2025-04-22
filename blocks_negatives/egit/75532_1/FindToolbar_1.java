		progressBar = new ProgressBar(this, SWT.HORIZONTAL);
		GridData findProgressBarData = new GridData();
		findProgressBarData.heightHint = 12;
		findProgressBarData.widthHint = 35;
		progressBar.setLayoutData(findProgressBarData);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);


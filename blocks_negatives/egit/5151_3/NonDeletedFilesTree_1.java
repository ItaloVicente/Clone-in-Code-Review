		GridDataFactory.fillDefaults().indent(0, 0).grab(true, true).applyTo(
				main);
		main.setLayout(new GridLayout(2, false));
		Label repoLabel = new Label(main, SWT.NONE);
		repoLabel.setText(UIText.NonDeletedFilesTree_RepositoryLabel);
		Text repoPath = new Text(main, SWT.BORDER | SWT.READ_ONLY);
		repoPath.setText(repository.getWorkTree().getPath());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(repoPath);


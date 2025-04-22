	public CommitDialog(Shell parentShell) {
		super(parentShell);
	}

	public String getCommitMessage() {
		return commitMessage;
	}

	public void setCommitMessage(String s) {
		this.commitMessage = s;
	}

	public void setSelectedFiles(IFile[] items) {
		Collections.addAll(selectedFiles, items);
	}

	public IFile[] getSelectedFiles() {
		return selectedFiles.toArray(new IFile[0]);
	}

	public void setPreselectedFiles(Set<IFile> preselectedFiles) {
		Assert.isNotNull(preselectedFiles);
		this.preselectedFiles = preselectedFiles;
	}

	public void setFiles(Set<IFile> files, Map<Repository, IndexDiff> indexDiffs) {
		items.clear();
		for (IFile file : files) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(file.getProject());
			Repository repo = repositoryMapping.getRepository();
			String path = repositoryMapping.getRepoRelativePath(file);
			CommitItem item = new CommitItem();
			item.status = getFileStatus(path, indexDiffs.get(repo));
			item.file = file;
			items.add(item);
		}
		Collections.sort(items, new Comparator<CommitItem>() {
			public int compare(CommitItem o1, CommitItem o2) {
				int diff = o1.status.ordinal() - o2.status.ordinal();
				if (diff != 0)
					return diff;
				diff = o1.file.getProject().getName().compareTo(
						o2.file.getProject().getName());
				if (diff != 0)
					return diff;
				return o1.file
				.getProjectRelativePath()
				.toString()
				.compareTo(
						o2.file.getProjectRelativePath().toString());
			}
		});
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCommitter() {
		return committer;
	}

	public void setCommitter(String committer) {
		this.committer = committer;
	}

	public void setPreviousAuthor(String previousAuthor) {
		this.previousAuthor = previousAuthor;
	}

	public boolean isSignedOff() {
		return signedOff;
	}

	public void setSignedOff(boolean signedOff) {
		this.signedOff = signedOff;
	}

	public boolean isAmending() {
		return amending;
	}

	public void setAmending(boolean amending) {
		this.amending = amending;
	}

	public void setPreviousCommitMessage(String string) {
		this.previousCommitMessage = string;
	}

	public void setAmendAllowed(boolean amendAllowed) {
		this.amendAllowed = amendAllowed;
	}

	public void setAllowToChangeSelection(boolean allowToChangeSelection) {
		this.allowToChangeSelection = allowToChangeSelection;
	}

	public boolean getCreateChangeId() {
		return createChangeId;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.SELECT_ALL_ID, UIText.CommitDialog_SelectAll, false);
		createButton(parent, IDialogConstants.DESELECT_ALL_ID, UIText.CommitDialog_DeselectAll, false);

		createButton(parent, IDialogConstants.OK_ID, UIText.CommitDialog_Commit, true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		parent.getShell().setText(UIText.CommitDialog_CommitChanges);

		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		Label label = new Label(container, SWT.LEFT);
		label.setText(UIText.CommitDialog_CommitMessage);
		label.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).grab(true, false).create());

		commitText = new SpellcheckableMessageArea(container, commitMessage);
		Point size = commitText.getTextWidget().getSize();
		int minHeight = commitText.getTextWidget().getLineHeight() * 3;
		commitText.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
				.hint(size).minSize(size.x, minHeight).align(SWT.FILL, SWT.FILL).create());
		commitText.setText(calculateCommitMessage());

		commitText.getTextWidget().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent event) {
				if (event.keyCode == SWT.CR
						&& (event.stateMask & SWT.CONTROL) > 0) {
					okPressed();
				} else if (event.keyCode == SWT.TAB
						&& (event.stateMask & SWT.SHIFT) == 0) {
					event.doit = false;
					commitText.traverse(SWT.TRAVERSE_TAB_NEXT);
				}
			}
		});

		new Label(container, SWT.LEFT).setText(UIText.CommitDialog_Author);
		authorText = new Text(container, SWT.BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		if (author != null)
			authorText.setText(author);

		authorHandler = UIUtils.addPreviousValuesContentProposalToText(authorText, AUTHOR_VALUES_PREF);
		new Label(container, SWT.LEFT).setText(UIText.CommitDialog_Committer);
		committerText = new Text(container, SWT.BORDER);
		committerText.setLayoutData(GridDataFactory.fillDefaults().grab(true, false).create());
		if (committer != null)
			committerText.setText(committer);
		committerText.addModifyListener(new ModifyListener() {
			String oldCommitter = committerText.getText();
			public void modifyText(ModifyEvent e) {
				if (signedOffButton.getSelection()) {
					String newCommitter = committerText.getText();
					String oldSignOff = getSignedOff(oldCommitter);
					String newSignOff = getSignedOff(newCommitter);
					commitText.setText(replaceSignOff(commitText.getText(), oldSignOff, newSignOff));
					oldCommitter = newCommitter;
				}
			}
		});

		committerHandler = UIUtils.addPreviousValuesContentProposalToText(committerText, COMMITTER_VALUES_PREF);

		Link preferencesLink = new Link(container, SWT.NONE);
		preferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		preferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String preferencePageId = "org.eclipse.egit.ui.internal.preferences.CommitDialogPreferencePage"; //$NON-NLS-1$
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getShell(), preferencePageId,
								new String[] { preferencePageId }, null);
				dialog.open();
				commitText.reconfigure();
			}
		});

		amendingButton = new Button(container, SWT.CHECK);
		if (amending) {
			amendingButton.setSelection(amending);
			amendingButton.setEnabled(false); // if already set, don't allow any changes
			authorText.setText(previousAuthor);
			saveOriginalChangeId();
		} else if (!amendAllowed) {
			amendingButton.setEnabled(false);
			originalChangeId = null;
		}
		amendingButton.addSelectionListener(new SelectionListener() {
			boolean alreadyAdded = false;
			public void widgetSelected(SelectionEvent arg0) {
				if (!amendingButton.getSelection()) {
					originalChangeId = null;
					authorText.setText(author);
				}
				else {
					saveOriginalChangeId();
					if (!alreadyAdded) {
						alreadyAdded = true;
						commitText.setText(previousCommitMessage.replaceAll(
								"\n", Text.DELIMITER)); //$NON-NLS-1$
					}
					authorText.setText(previousAuthor);
				}
				refreshChangeIdText();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}


		warningComposite = new Composite(historyControl, SWT.NONE);
		warningComposite.setLayout(new GridLayout(3, false));
		Label warningLabel = new Label(warningComposite, SWT.NONE);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		warningText = new Text(warningComposite, SWT.READ_ONLY);
		warningText.setToolTipText(UIText.GitHistoryPage_IncompleteListTooltip);

		Link preferencesLink = new Link(warningComposite, SWT.NONE);
		preferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		preferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String preferencePageId = "org.eclipse.egit.ui.GitPreferences"; //$NON-NLS-1$
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getSite().getShell(), preferencePageId,
								new String[] { preferencePageId }, null);
				dialog.open();
			}
		});


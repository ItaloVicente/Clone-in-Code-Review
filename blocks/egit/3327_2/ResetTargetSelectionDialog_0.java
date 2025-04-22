		new Composite(main, SWT.NONE);
		Group g2 = new Group(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(g2);
		g2.setLayout(new GridLayout(2, false));
		Label label = new Label(g2, SWT.NONE);
		label.setText("Commit-ish"); //$NON-NLS-1$
		anySha1 = new Text(g2, SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(anySha1);
		anySha1.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
			}

			public void focusGained(FocusEvent e) {
				branchTree.setSelection(null);
			}
		});
		anySha1.addModifyListener(new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				String text = anySha1.getText();
				if (text.length() == 0) {
					parsedCommitish = null;
					setMessage(""); //$NON-NLS-1$
					return;
				}
				try {
					ObjectId resolved = repo.resolve(text+"^{commit}"); //$NON-NLS-1$
					if (resolved == null) {
						setMessage("Unresolved ref expression", IMessageProvider.ERROR); //$NON-NLS-1$
						getButton(OK).setEnabled(false);
						parsedCommitish = null;
						return;
					} else {
						setMessage("Resolved as " + resolved.getName(), IMessageProvider.NONE); //$NON-NLS-1$
						parsedCommitish = text;
						getButton(OK).setEnabled(true);
					}
				} catch (IOException e1) {
					setMessage(e1.getMessage(), IMessageProvider.ERROR);
					getButton(OK).setEnabled(false);
					parsedCommitish = null;
				}
			}
		});
		branchTree.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				if (!event.getSelection().isEmpty())
					anySha1.setText(""); //$NON-NLS-1$
			}
		});

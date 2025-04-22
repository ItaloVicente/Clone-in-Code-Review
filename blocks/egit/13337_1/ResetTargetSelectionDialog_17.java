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
						setMessage(
								UIText.ResetTargetSelectionDialog_UnresolvableExpressionError,
								IMessageProvider.ERROR);
						getButton(OK).setEnabled(false);
						parsedCommitish = null;
						sha1.setText(""); //$NON-NLS-1$
						subject.setText(""); //$NON-NLS-1$
						author.setText(""); //$NON-NLS-1$
						committer.setText(""); //$NON-NLS-1$
						return;
					} else {
						setMessage(""); //$NON-NLS-1$
						parsedCommitish = text;
						getButton(OK).setEnabled(true);
						RevWalk rw = new RevWalk(repo);
						RevCommit commit = rw.parseCommit(resolved);
						sha1.setText(AbbreviatedObjectId.fromObjectId(commit)
								.name());
						subject.setText(commit.getShortMessage());
						author.setText(commit.getAuthorIdent().getName()
								+ " <" //$NON-NLS-1$
								+ commit.getAuthorIdent().getEmailAddress()
								+ "> " + gitDateFormatter.formatDate(commit.getAuthorIdent())); //$NON-NLS-1$
						committer.setText(commit.getCommitterIdent().getName()
								+ " <" //$NON-NLS-1$
								+ commit.getCommitterIdent().getEmailAddress()
								+ "> " + gitDateFormatter.formatDate(commit.getCommitterIdent())); //$NON-NLS-1$
						rw.dispose();
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
				if (!event.getSelection().isEmpty()) {
					String refName = refNameFromDialog();
					if (refName != null) {
						anySha1.setText(refName);
						anySha1.selectAll();
					}
				}
			}
		});

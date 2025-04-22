		GpgSigner signer = GpgSigner.getDefault();
		if (signer != null) {
			GpgConfig gpgConfig = new GpgConfig(repo.getConfig());
			PersonIdent tagger = new PersonIdent(repo);
			if (SignatureUtils.checkSigningKey(signer,
					gpgConfig.getSigningKey(), tagger)) {
				signAll = gpgConfig.isSignAllTags();
				signAnnotated = gpgConfig.isSignAnnotated();
				signButton = new Button(left, SWT.CHECK);
				signButton.setText("Create a signed annotated tag"); //$NON-NLS-1$
				signButton.setToolTipText(
						"If checked the tag will be signed, and will forcibly be an annotated tag, even if it has no message"); //$NON-NLS-1$
				signButton.setSelection(signAll);
				signButton.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						super.widgetSelected(e);
						signExplicit = true;
					}
				});
			}
		}


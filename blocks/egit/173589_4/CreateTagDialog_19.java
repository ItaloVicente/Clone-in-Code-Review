		GpgSigner signer = GpgSigner.getDefault();
		if (signer instanceof GpgObjectSigner) {
			GpgConfig gpgConfig = new GpgConfig(repo.getConfig());
			PersonIdent tagger = new PersonIdent(repo);
			if (SignatureUtils.checkSigningKey(signer,
					gpgConfig.getSigningKey(), tagger)) {
				signAll = gpgConfig.isSignAllTags();
				signAnnotated = gpgConfig.isSignAnnotated();
				signButton = new Button(left, SWT.CHECK);
				signButton.setText(UIText.CreateTagDialog_signTag);
				signButton
						.setToolTipText(UIText.CreateTagDialog_signTagToolTip);
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


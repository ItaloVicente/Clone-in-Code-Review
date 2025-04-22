
		boolean lfsAvailable = LfsFactory.getInstance().isAvailable()
				&& LfsFactory.getInstance().getInstallCommand() != null;
		Group lfsGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridLayoutFactory.fillDefaults().applyTo(lfsGroup);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(lfsGroup);
		lfsGroup.setText(
				lfsAvailable ? UIText.GitPreferenceRoot_lfsSupportCaption : UIText.GitPreferenceRoot_lfsSupportCaptionNotAvailable);
		Button lfsEnable = new Button(lfsGroup, SWT.PUSH);
		lfsEnable.setEnabled(lfsAvailable);
		lfsEnable.setText(UIText.GitPreferenceRoot_lfsSupportInstall);
		lfsEnable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				LfsInstallCommand cmd = LfsFactory.getInstance()
						.getInstallCommand();
				try {
					if (cmd != null) {
						cmd.call();

						MessageDialog.openInformation(getShell(),
								UIText.GitPreferenceRoot_lfsSupportSuccessTitle,
								UIText.GitPreferenceRoot_lfsSupportSuccessMessage);
					}
				} catch (Exception ex) {
					Activator.handleError(
							UIText.ConfigurationChecker_installLfsCannotInstall,
							ex, true);
				}
			}
		});
		updateMargins(lfsGroup);

		gpgSigner = new ComboFieldEditor(GitCorePreferences.core_gpgSigner,
				UIText.CommittingPreferencePage_gpgSignerLabel,
				GPG_SIGNER_NAMES_AND_VALUES, generalGroup) {

			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				super.setPreferenceStore(
						store == null ? null : getSecondaryPreferenceStore());
			}
		};
		addField(gpgSigner);
		gpgExecutable = new FullWidthFileFieldEditor(
				GitCorePreferences.core_gpgExecutable,
				UIText.CommittingPreferencePage_gpgExecutableLabel, true,
				generalGroup) {

			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				super.setPreferenceStore(
						store == null ? null : getSecondaryPreferenceStore());
			}

			@Override
			protected boolean doCheckState() {
				Text text = getTextControl();
				if (text != null) {
					String value = text.getText().trim();
					if (!value.isEmpty()) {
						try {
							if (!Files.isExecutable(Paths.get(value))) {
								setErrorMessage(
										UIText.CommittingPreferencePage_gpgExecutableNotExecutable);
								return false;
							}
						} catch (InvalidPathException e) {
							setErrorMessage(
									UIText.CommittingPreferencePage_gpgExecutableInvalid);
							return false;
						}
					}
				}
				return super.doCheckState();
			}
		};
		addField(gpgExecutable);
		gpgExecutable.getLabelControl(generalGroup).setToolTipText(
				UIText.CommittingPreferencePage_gpgExecutableTooltip);


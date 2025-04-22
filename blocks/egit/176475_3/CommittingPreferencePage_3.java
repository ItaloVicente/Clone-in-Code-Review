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

		addField(new BooleanFieldEditor(
				GitCorePreferences.core_saveCredentialsInSecureStore,
				UIText.GitPreferenceRoot_SecureStoreUseForSshKeys,
				secureGroup) {

			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				super.setPreferenceStore(
						store == null ? null : getSecondaryPreferenceStore());
			}
		});

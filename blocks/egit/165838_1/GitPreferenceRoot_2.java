				remoteConnectionsGroup) {

			@Override
			public void setPreferenceStore(IPreferenceStore store) {
				super.setPreferenceStore(
						store == null ? null : getSecondaryPreferenceStore());
			}
		};

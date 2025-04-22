		IntegerFieldEditor pullEditor = new IntegerFieldEditor(
				GitCorePreferences.core_maxPullThreadsCount,
				UIText.GitPreferenceRoot_MaxPullThreadsCount,
				remoteConnectionsGroup) {
			@Override
			public IPreferenceStore getPreferenceStore() {
				return getSecondaryPreferenceStore();
			}
		};
		pullEditor.getLabelControl(remoteConnectionsGroup).setToolTipText(
				UIText.GitPreferenceRoot_MaxPullThreadsCountTooltip);
		addField(pullEditor);
		pullEditor.setPreferenceStore(getSecondaryPreferenceStore());


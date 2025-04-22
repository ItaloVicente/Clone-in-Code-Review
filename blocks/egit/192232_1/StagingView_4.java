
	private static class PushSettings extends DropDownMenuAction {

		private static final String PER_REPO_SETTINGS = StagingView.class
				.getName() + ".PER_REPOSITORY_SETTINGS"; //$NON-NLS-1$

		private static final String EMPTY = ""; //$NON-NLS-1$

		private boolean forceState;

		private boolean dialogState;

		IDialogSettings savedSettings;

		String key;

		String[] values;

		public PushSettings() {
			super(UIText.StagingView_PushSettings);
			setImageDescriptor(UIIcons.SETTINGS);
			savedSettings = DialogSettings.getOrCreateSection(
					Activator.getDefault().getDialogSettings(),
					PER_REPO_SETTINGS);
		}

		public void refresh(Repository repository) {
			if (repository == null) {
				savedSettings = null;
				key = null;
				forceState = false;
				dialogState = false;
				setImageDescriptor(UIIcons.SETTINGS);
				setEnabled(false);
			} else {
				key = repository.getDirectory().getAbsolutePath();
				values = savedSettings.getArray(key);
				if (values == null || values.length < 2) {
					values = new String[] { Boolean.FALSE.toString(), EMPTY };
				}
				forceState = Boolean.parseBoolean(values[0]);
				if (StringUtils.isEmptyOrNull(values[1])) {
					Activator.getDefault().getPreferenceStore().getBoolean(
							UIPreferences.ALWAYS_SHOW_PUSH_WIZARD_ON_COMMIT);
				} else {
					dialogState = Boolean.parseBoolean(values[1]);
				}
				updateImage();
				setEnabled(true);
			}
		}

		private void updateImage() {
			setImageDescriptor(
					forceState ? UIIcons.SETTINGS_FORCE : UIIcons.SETTINGS);
		}

		@Override
		protected Collection<IContributionItem> getActions() {
			if (!isEnabled()) {
				return Collections.emptyList();
			}
			List<IContributionItem> items = new ArrayList<>(2);
			Action forceAction = new Action(UIText.StagingView_PushForce,
					IAction.AS_CHECK_BOX) {

				@Override
				public void run() {
					forceState = isChecked();
					values[0] = Boolean.toString(forceState);
					savedSettings.put(key, values);
					updateImage();
				}
			};
			forceAction.setChecked(forceState);
			items.add(new ActionContributionItem(forceAction));
			Action showDialogAction = new Action(
					UIText.StagingView_PushDialogAlways,
					IAction.AS_CHECK_BOX) {

				@Override
				public void run() {
					dialogState = isChecked();
					values[1] = Boolean.toString(dialogState);
					savedSettings.put(key, values);
				}
			};
			showDialogAction.setChecked(dialogState);
			items.add(new ActionContributionItem(showDialogAction));
			return items;
		}

		public boolean isForce() {
			return forceState;
		}

		public boolean alwaysShowDialog() {
			return dialogState;
		}
	}

		boolean shouldShow = !result.isSuccessfulConnectionForAnyURI()
				|| Activator.getDefault().getPreferenceStore()
						.getBoolean(UIPreferences.SHOW_PUSH_CONFIRM);

		if (!shouldShow) {
			Activator
					.getDefault()
					.getLog()
					.log(new org.eclipse.core.runtime.Status(IStatus.INFO,
							Activator.getPluginId(), NLS.bind(
									UIText.ResultDialog_label, sourceString)));
			return;
		}


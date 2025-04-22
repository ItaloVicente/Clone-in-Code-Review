						boolean enabled = enableLinkedResourcesButton
								.getSelection();
						Preferences preferences = ResourcesPlugin.getPlugin()
								.getPluginPreferences();
						preferences.setValue(
								ResourcesPlugin.PREF_DISABLE_LINKING, !enabled);

						updateWidgetState(enabled);
						if (enabled) {
							MessageDialog
									.openWarning(
											getShell(),
											IDEWorkbenchMessages.LinkedResourcesPreference_linkedResourcesWarningTitle,
											IDEWorkbenchMessages.LinkedResourcesPreference_linkedResourcesWarningMessage);
						}
					}
				});

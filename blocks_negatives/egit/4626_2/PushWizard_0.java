						.findRemoteRefUpdatesFor(localDb, fetchSpecs,
								fetchSpecs);
				if (updates.isEmpty()) {
					ErrorDialog.openError(getShell(),
							UIText.PushWizard_missingRefsTitle, null,
							new Status(IStatus.ERROR, Activator.getPluginId(),
									UIText.PushWizard_missingRefsMessage));
					return null;
				}

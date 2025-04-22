			toolName = DiffMergeSettings
					.readExternalToolFromGitConfig(
							c -> getMergeToolFromGitConfig(c), repository);
			if (toolName.isEmpty()) {
				toolName = Optional.of(""); //$NON-NLS-1$
				Activator.handleError(
						UIText.MergeToolActionHandler_noToolConfiguredDialogTitle,
						null, true);
			}

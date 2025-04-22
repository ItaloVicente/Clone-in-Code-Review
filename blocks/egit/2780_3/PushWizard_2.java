			if (calledFromRepoPage) {
				final Collection<RefSpec> fetchSpecs = new ArrayList<RefSpec>();
				fetchSpecs.addAll(config.getPushRefSpecs());
				if (fetchSpecs.isEmpty())
					fetchSpecs.add(PushOperationUI.DEFAULT_PUSH_REF_SPEC);
				final Collection<RemoteRefUpdate> updates = Transport
						.findRemoteRefUpdatesFor(localDb, fetchSpecs,
								fetchSpecs);
				if (updates.isEmpty()) {
					ErrorDialog.openError(getShell(),
							UIText.PushWizard_missingRefsTitle, null,
							new Status(IStatus.ERROR, Activator.getPluginId(),
									UIText.PushWizard_missingRefsMessage));
					return null;
				}
				spec = new PushOperationSpecification();
				for (final URIish uri : repoPage.getSelection().getPushURIs())
					spec.addURIRefUpdates(uri, ConfirmationPage
							.copyUpdates(updates));
			} else if (confirmPage.isConfirmed()) {

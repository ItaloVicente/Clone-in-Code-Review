		} else {
			RepositorySelectionPage sp;
			if (pushMode) {
				sp = new RepositorySelectionPage(pushMode, null,
						myConfiguration.getString(RepositoriesView.REMOTE,
								myRemoteName, RepositoriesView.PUSHURL));
			} else {
				sp = new RepositorySelectionPage(pushMode, null,
						myConfiguration.getString(RepositoriesView.REMOTE,
								myRemoteName, RepositoriesView.URL));
			}

			addPage(sp);
			RefSpecPage rsp = new RefSpecPage(repository, pushMode, sp);
			rsp.setConfigName(myRemoteName);
			addPage(rsp);
			setWindowTitle(NLS.bind(
					UIText.ConfigureRemoteWizard_WizardTitle_Change,
					myRemoteName));
		}

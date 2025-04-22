						public void run() {
							runCloneOperation(getContainer());
							cloneDestination.saveSettingsForClonedRepo();
						}});
				} catch (URISyntaxException e) {
					Activator.error(UIText.GitImportWizard_errorParsingURI, e);
				}
			}
			super.setVisible(visible);
		}
	};

	private GitProjectsImportPage projectsImportPage = new GitProjectsImportPage() ;

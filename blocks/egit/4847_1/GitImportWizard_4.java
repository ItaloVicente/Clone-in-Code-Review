						public void run() {
							runCloneOperation(getContainer());
							cloneDestination.saveSettingsForClonedRepo();
						}});
				} catch (URISyntaxException e) {

				}
			}
			super.setVisible(visible);
		}
	};

	private GitProjectsImportPage projectsImportPage = new GitProjectsImportPage() ;

		if (repositories == null) {
			repositories = new ArrayList<Repository>();
			List<String> repoPaths = Activator.getDefault().getRepositoryUtil().getConfiguredRepositories();
			RepositoryCache repositoryCache = org.eclipse.egit.core.Activator.getDefault().getRepositoryCache();
			for (String repoPath : repoPaths) {
				try {
					Repository repository = repositoryCache.lookupRepository(new File(repoPath));
					repositories.add(repository);
				} catch (IOException e) {
					continue;
				}
			}
		}
	}

	private String[] getRepositoryComboItems() {
		List<String> items = new ArrayList<String>();
		for (Repository repository : repositories) {
			String repoName = repository.getDirectory().getParentFile().getName();
			items.add(repoName);
		}
		return items.toArray(new String[items.size()]);
	}

	private void showRepositoryConfiguration(int index) {
		Repository repository = repositories.get(index);
		ConfigurationEditorComponent editor = repoConfigEditors.get(repository);
		if (editor == null) {
			editor = createConfigurationEditor(repository);
			repoConfigEditors.put(repository, editor);
		}
		repoConfigStackLayout.topControl = editor.getContents();
		repoConfigComposite.layout();
	}

	private ConfigurationEditorComponent createConfigurationEditor(final Repository repository) {
		StoredConfig repositoryConfig;
		if (repository.getConfig() instanceof FileBasedConfig) {
			File configFile = ((FileBasedConfig) repository.getConfig()).getFile();
			repositoryConfig = new FileBasedConfig(configFile, repository
					.getFS());
		} else {
			repositoryConfig = repository.getConfig();
		}
		ConfigurationEditorComponent editorComponent = new ConfigurationEditorComponent(repoConfigComposite, repositoryConfig, true) {
			@Override
			protected void setErrorMessage(String message) {
				GlobalConfigurationPreferencePage.this.setErrorMessage(message);
			}

			@Override
			protected void setDirty(boolean dirty) {
				if (dirty)
					dirtyRepositories.add(repository);
				else
					dirtyRepositories.remove(repository);
				updateApplyButton();
			}
		};
		editorComponent.createContents();
		return editorComponent;

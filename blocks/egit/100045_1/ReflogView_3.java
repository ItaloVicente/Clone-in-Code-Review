		formHead = form.getHead();
		if (formHead instanceof FormHeading) {
			headMenuManager = ((FormHeading) formHead).getMenuManager();
			RepositoryUtil util = org.eclipse.egit.core.Activator.getDefault()
					.getRepositoryUtil();
			if (!util.getRepositories().isEmpty()) {
				headMenuManager.add(new Action() {
				});
			}
			headMenuManager.setRemoveAllWhenShown(true);
			headMenuManager.addMenuListener(
					manager -> RepositoryMenuUtil.fillRepositories(manager,
							false, repo -> reactOnSelection(
									new StructuredSelection(repo))));
			prefListener = event -> {
				if (!RepositoryUtil.PREFS_DIRECTORIES_REL
						.equals(event.getKey())) {
					return;
				}
				updateHeadMenu(event.getNewValue());
			};
			util.getPreferences().addPreferenceChangeListener(prefListener);
		}


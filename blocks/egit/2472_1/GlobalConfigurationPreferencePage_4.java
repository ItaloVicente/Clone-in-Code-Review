		for (Repository repository : dirtyRepositories) {
			ConfigurationEditorComponent editor = repoConfigEditors.get(repository);
			try {
				editor.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				ok = false;
			}
		}

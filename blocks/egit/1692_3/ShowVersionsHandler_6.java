			String message = NLS.bind(
					UIText.GitHistoryPage_notContainedInCommits, gitPath,
					commitList.toString());
			IStatus error = new Status(IStatus.ERROR, Activator.getPluginId(),
					message);
			EgitUiEditorUtils.openErrorEditor(getPart(event).getSite()
					.getPage(), error,
					UIText.ShowVersionsHandler_ErrorOpeningFileTitle, null);

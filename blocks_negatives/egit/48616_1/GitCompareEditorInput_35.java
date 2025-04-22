			CompareConfiguration config = getCompareConfiguration();
			config.setLeftLabel(compareVersion);
			config.setRightLabel(baseVersion);
			if (resources.length == 0) {
				Object[] titleParameters = new Object[] {
						Activator.getDefault().getRepositoryUtil()
								.getRepositoryName(repository),
						CompareUtils.truncatedRevision(compareVersion),
						CompareUtils.truncatedRevision(baseVersion) };
				setTitle(NLS.bind(UIText.GitCompareEditorInput_EditorTitle,
						titleParameters));
			} else if (resources.length == 1) {
				Object[] titleParameters = new Object[] {
						resources[0].getFullPath().makeRelative().toString(),
						CompareUtils.truncatedRevision(compareVersion),
						CompareUtils.truncatedRevision(baseVersion) };
				setTitle(NLS.bind(
						UIText.GitCompareEditorInput_EditorTitleSingleResource,
						titleParameters));
			} else
				setTitle(NLS
						.bind(
								UIText.GitCompareEditorInput_EditorTitleMultipleResources,
								CompareUtils.truncatedRevision(compareVersion),
								CompareUtils.truncatedRevision(baseVersion)));

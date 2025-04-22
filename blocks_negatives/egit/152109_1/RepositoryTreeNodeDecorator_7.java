			try {
				decorateText(node, repository, decoration);
			} catch (IOException e) {
				Activator.logError(MessageFormat.format(
						UIText.GitLabelProvider_UnableToRetrieveLabel,
						element.toString()), e);
			}

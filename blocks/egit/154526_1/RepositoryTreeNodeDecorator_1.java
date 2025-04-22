		try {
			decorateText(node, repository, decoration);
			decorateIcon(node, repository, decoration);
		} catch (IOException e) {
			Activator.logError(MessageFormat.format(
					UIText.GitLabelProvider_UnableToRetrieveLabel,
					node.toString()), e);
		}

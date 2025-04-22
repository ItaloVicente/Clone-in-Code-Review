		Runnable query = new Runnable() {
			@Override
			public void run() {
				String pathName = destination.getFullPath().makeRelative()
						.toString();
				String message = RESOURCE_EXISTS_MESSAGE;
				String title = RESOURCE_EXISTS_TITLE;
				if (destination.getType() == IResource.PROJECT) {
					message = PROJECT_EXISTS_MESSAGE;
					title = PROJECT_EXISTS_TITLE;
				}
				result[0] = MessageDialog.openQuestion(shell,
						title, MessageFormat.format(message,
								new Object[] { pathName }));

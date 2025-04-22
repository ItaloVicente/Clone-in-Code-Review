			String uriParameter = event
					.getParameter(REPOSITORY_URI_PARAMETER_ID);
			wizard = uriParameter != null && GitUrlChecker
					.isValidGitUrl(GitUrlChecker.sanitizeAsGitUrl(uriParameter))
							? new GitCloneWizard(uriParameter)
							: new GitCloneWizard();

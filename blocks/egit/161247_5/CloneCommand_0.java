			String uriParameter = event
					.getParameter(REPOSITORY_URI_PARAMETER_ID);
			wizard = uriParameter != null && GitUrlChecker
					.isValidGitUrl(GitUrlChecker.sanitizeAsGitUrl(uriParameter))
							? new GitCloneWizard(GitUrlChecker
									.sanitizeAsGitUrl(uriParameter))
							: new GitCloneWizard();

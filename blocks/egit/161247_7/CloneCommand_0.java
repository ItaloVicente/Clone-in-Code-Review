			String uriParameter = event
					.getParameter(REPOSITORY_URI_PARAMETER_ID);
			if (uriParameter != null) {
				String sanitizedURI = GitUrlChecker
						.sanitizeAsGitUrl(uriParameter);

				wizard = GitUrlChecker.isValidGitUrl(sanitizedURI)
						? new GitCloneWizard(sanitizedURI)
						: new GitCloneWizard();
			} else {
				wizard = new GitCloneWizard();
			}

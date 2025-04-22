				String defaultAuths = getDefaultPreferredAuthentications();
				if (defaultAuths != null) {
					jgitClient.setAttribute(
							JGitSshClient.PREFERRED_AUTHENTICATIONS
							defaultAuths);
				}

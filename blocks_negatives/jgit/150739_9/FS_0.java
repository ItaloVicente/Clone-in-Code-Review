							JGitText.get().cannotSaveConfig, userConfig), e);
					break;
				} catch (ConfigInvalidException e) {
					LOG.error(MessageFormat.format(
							JGitText.get().repositoryConfigFileInvalid,
							userConfig, e.getMessage()));

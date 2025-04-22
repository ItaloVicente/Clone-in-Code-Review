				userConfig.load(false);
			} catch (IOException e) {
				LOG.error(MessageFormat.format(JGitText.get().readConfigFailed,
						userConfig.getFile().getAbsolutePath()), e);
			} catch (ConfigInvalidException e) {
				LOG.error(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid,
						userConfig.getFile().getAbsolutePath(),
						e.getMessage()));

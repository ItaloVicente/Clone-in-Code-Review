			String branch = initialBranch;
			if (StringUtils.isEmptyOrNull(initialBranch)) {
				branch = SystemReader.getInstance().getUserConfig().getString(
						ConfigConstants.CONFIG_INIT_SECTION
						ConfigConstants.CONFIG_KEY_DEFAULT_BRANCH);
				if (StringUtils.isEmptyOrNull(branch)) {
					branch = Constants.MASTER;
				}
				if (!Repository.isValidRefName(Constants.R_HEADS + branch)) {
					throw new InvalidRefNameException(MessageFormat
							.format(JGitText.get().branchNameInvalid
				}
			}
			builder.setInitialBranch(branch);

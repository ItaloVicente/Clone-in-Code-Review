	private void handleDivergingDevelop(GitFlowConfig config)
			throws CoreException {
		try {
			BranchTrackingStatus developStatus = BranchTrackingStatus
					.of(repository.getRepository(), config.getDevelop());
			if (developStatus == null) {
				return;
			}

			if (developStatus.getBehindCount() > 0) {
				String message = NLS.bind(
						CoreText.FeatureStartOperation_divergingDevelop,
						config.getDevelop(), getOriginDevelopName(developStatus));
				if (developStatus.getAheadCount() == 0) {
					message += "\n" + NLS.bind( //$NON-NLS-1$
							CoreText.FeatureStartOperation_andBranchMayBeFastForwarded,
							config.getDevelop());
				}

				throw new CoreException(Activator.error(message));
			} else if (developStatus.getAheadCount() > 0) {
				String message = NLS.bind(
						CoreText.FeatureStartOperation_divergingDevelop,
						config.getDevelop(),
						getOriginDevelopName(developStatus));
				message += "\n" + NLS.bind( //$NON-NLS-1$
						CoreText.FeatureStartOperation_andLocalDevelopIsAheadOfOrigin,
						config.getDevelop(),
						getOriginDevelopName(developStatus));
				Activator.logInfo(message);
			}
		} catch (IOException e) {
			throw new CoreException(Activator.error(e));
		}
	}

	private String getOriginDevelopName(BranchTrackingStatus developStatus) {
		return developStatus.getRemoteTrackingBranch()
				.substring(Constants.R_REMOTES.length());
	}


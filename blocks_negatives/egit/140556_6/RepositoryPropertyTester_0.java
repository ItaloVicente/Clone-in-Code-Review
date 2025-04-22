			if (IS_INITIALIZED.equals(property)) {
				return gitFlowRepository.getConfig().isInitialized();
			} else if (IS_FEATURE.equals(property)) {
				return gitFlowRepository.isFeature();
			} else if (IS_RELEASE.equals(property)) {
				return gitFlowRepository.isRelease();
			} else if (IS_HOTFIX.equals(property)) {
				return gitFlowRepository.isHotfix();
			} else if (IS_DEVELOP.equals(property)) {
				return gitFlowRepository.isDevelop();
			} else if (IS_MASTER.equals(property)) {
				return gitFlowRepository.isMaster();
			} else if (HAS_DEFAULT_REMOTE.equals(property)) {
				return gitFlowRepository.getConfig().hasDefaultRemote();

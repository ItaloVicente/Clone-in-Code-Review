			}
			if ("canFetchFromGerrit".equals(property)) { //$NON-NLS-1$
				return canFetchFromGerrit(repository);
			}
			if ("canPushToGerrit".equals(property)) { //$NON-NLS-1$
				return canPushToGerrit(repository);
			}

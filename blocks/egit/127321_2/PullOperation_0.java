		}
		RebaseResult rebaseResult = pullResult.getRebaseResult();
		if (rebaseResult != null
				&& rebaseResult.getStatus() == RebaseResult.Status.UP_TO_DATE) {
			return false;
		}

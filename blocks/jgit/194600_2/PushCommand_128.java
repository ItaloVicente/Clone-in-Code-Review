	private String determineRemote(Config config
			throws IOException {
		if (remoteName != null) {
			return remoteName;
		}
		Ref head = repo.exactRef(Constants.HEAD);
		String effectiveRemote = null;
		BranchConfig branchCfg = null;
		if (head != null && head.isSymbolic()) {
			String currentBranch = head.getLeaf().getName();
			branchCfg = new BranchConfig(config
					Repository.shortenRefName(currentBranch));
			effectiveRemote = branchCfg.getPushRemote();
		}
		if (effectiveRemote == null) {
			effectiveRemote = config.getString(
					ConfigConstants.CONFIG_REMOTE_SECTION
					ConfigConstants.CONFIG_KEY_PUSH_DEFAULT);
			if (effectiveRemote == null && branchCfg != null) {
				effectiveRemote = branchCfg.getRemote();
			}
		}
		if (effectiveRemote == null) {
			effectiveRemote = Constants.DEFAULT_REMOTE_NAME;
		}
		return effectiveRemote;
	}

	private String getCurrentBranch()
			throws IOException
		Ref head = repo.exactRef(Constants.HEAD);
		if (head != null && head.isSymbolic()) {
			return head.getLeaf().getName();
		}
		throw new DetachedHeadException();
	}

	private void determineDefaultRefSpecs(Config config)
			throws IOException
		if (pushDefault == null) {
			pushDefault = config.get(PushConfig::new).getPushDefault();
		}
		switch (pushDefault) {
		case CURRENT:
			refSpecs.add(new RefSpec(getCurrentBranch()));
			break;
		case MATCHING:
			break;
		case NOTHING:
			throw new InvalidRefNameException(
					JGitText.get().pushDefaultNothing);
		case SIMPLE:
		case UPSTREAM:
			String currentBranch = getCurrentBranch();
			BranchConfig branchCfg = new BranchConfig(config
					Repository.shortenRefName(currentBranch));
			String fetchRemote = branchCfg.getRemote();
			if (fetchRemote == null) {
				fetchRemote = Constants.DEFAULT_REMOTE_NAME;
			}
			boolean isTriangular = !fetchRemote.equals(remote);
			if (isTriangular) {
				if (PushDefault.UPSTREAM.equals(pushDefault)) {
					throw new InvalidRefNameException(MessageFormat.format(
							JGitText.get().pushDefaultTriangularUpstream
							remote
				}
				refSpecs.add(new RefSpec(currentBranch));
			} else {
				String trackedBranch = branchCfg.getMerge();
				if (branchCfg.isRemoteLocal() || trackedBranch == null
						|| !trackedBranch.startsWith(Constants.R_HEADS)) {
					throw new InvalidRefNameException(MessageFormat.format(
							JGitText.get().pushDefaultNoUpstream
							currentBranch));
				}
				if (PushDefault.SIMPLE.equals(pushDefault)
						&& !trackedBranch.equals(currentBranch)) {
					throw new InvalidRefNameException(MessageFormat.format(
							JGitText.get().pushDefaultSimple
							trackedBranch));
				}
				refSpecs.add(new RefSpec(currentBranch + ':' + trackedBranch));
			}
			break;
		default:
			throw new InvalidRefNameException(MessageFormat
					.format(JGitText.get().pushDefaultUnknown
		}
	}


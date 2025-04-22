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
			setPushAll();
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


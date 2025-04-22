		if (obj instanceof GitModelCommit) {
			GitModelCommit objCommit = (GitModelCommit) obj;

			return objCommit.getBaseCommit().equals(baseCommit)
					&& objCommit.getRemoteCommit().equals(remoteCommit) &&
					objCommit.getLocation().equals(getLocation());
		}

		return false;

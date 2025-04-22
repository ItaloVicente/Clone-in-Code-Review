		if (obj instanceof GitModelCommit) {
			GitModelCommit objCommit = (GitModelCommit) obj;

			RevCommit objBaseCommit = objCommit.getBaseCommit();
			return objBaseCommit == null ? baseCommit == null : objBaseCommit
					.equals(baseCommit)
					&& objCommit.getRemoteCommit().equals(remoteCommit)
					&& objCommit.getLocation().equals(getLocation());
		}

		return false;

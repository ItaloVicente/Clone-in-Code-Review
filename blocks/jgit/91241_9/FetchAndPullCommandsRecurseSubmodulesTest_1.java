		FetchResult result;

		if (fetch) {
			result = git2.fetch().setRemote(REMOTE).setRefSpecs(REFSPEC)
					.setRecurseSubmodules(mode).call();
		} else {
			result = git2.pull().setRemote(REMOTE).setRebase(true)
					.setRecurseSubmodules(mode).call().getFetchResult();
		}

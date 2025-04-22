		FetchV2Request req = reqBuilder.build();
		protocolV2Hook.onFetch(req);

		options = req.getOptions();
		wantIds.addAll(req.getWantsIds());
		clientShallowCommits.addAll(req.getClientShallowCommits());
		depth = req.getDepth();
		shallowSince = req.getShallowSince();
		filterBlobLimit = req.getFilterBlobLimit();
		shallowExcludeRefs = req.getShallowExcludeRefs();


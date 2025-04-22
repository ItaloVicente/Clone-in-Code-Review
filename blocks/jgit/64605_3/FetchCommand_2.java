		try (Transport transport = Transport.open(repo
			transport.setCheckFetchedObjects(checkFetchedObjects);
			transport.setRemoveDeletedRefs(isRemoveDeletedRefs());
			transport.setDryRun(dryRun);
			if (tagOption != null)
				transport.setTagOpt(tagOption);
			transport.setFetchThin(thin);
			configure(transport);

			FetchResult result = transport.fetch(monitor
			return result;

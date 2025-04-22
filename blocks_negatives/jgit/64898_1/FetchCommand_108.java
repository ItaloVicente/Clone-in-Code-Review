		try {
			Transport transport = Transport.open(repo, remote);
			try {
				transport.setCheckFetchedObjects(checkFetchedObjects);
				transport.setRemoveDeletedRefs(isRemoveDeletedRefs());
				transport.setDryRun(dryRun);
				if (tagOption != null)
					transport.setTagOpt(tagOption);
				transport.setFetchThin(thin);
				configure(transport);

				FetchResult result = transport.fetch(monitor, refSpecs);
				return result;
			} finally {
				transport.close();
			}

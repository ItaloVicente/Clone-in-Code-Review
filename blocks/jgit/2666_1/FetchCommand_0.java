				transport.setCheckFetchedObjects(checkFetchedObjects);
				transport.setRemoveDeletedRefs(removeDeletedRefs);
				transport.setTimeout(timeout);
				transport.setDryRun(dryRun);
				if (tagOption != null)
					transport.setTagOpt(tagOption);
				transport.setFetchThin(thin);
				if (credentialsProvider != null)
					transport.setCredentialsProvider(credentialsProvider);


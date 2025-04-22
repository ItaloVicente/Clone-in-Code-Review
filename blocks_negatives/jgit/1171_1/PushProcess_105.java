			res.setAdvertisedRefs(transport.getURI(), connection.getRefsMap());
			res.setRemoteUpdates(toPush);
			monitor.endTask();

			final Map<String, RemoteRefUpdate> preprocessed = prepareRemoteUpdates();
			if (transport.isDryRun())
				modifyUpdatesForDryRun();
			else if (!preprocessed.isEmpty())
				connection.push(monitor, preprocessed);

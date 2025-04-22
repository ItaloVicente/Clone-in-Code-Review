		final List<RefUpdateElement> results = new ArrayList<RefUpdateElement>();

		for (URIish uri : result.getURIs())
			if (result.isSuccessfulConnection(uri))
				for (RemoteRefUpdate update : result.getPushResult(uri)
						.getRemoteUpdates())
					results.add(new RefUpdateElement(result, update, uri,
							reader, repo));

		treeViewer.setInput(results.toArray());

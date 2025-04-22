		AsynchronousRefProposalProvider candidateProvider = new AsynchronousRefProposalProvider(
				getContainer(), remoteBranchNameText, () -> {
					RemoteConfig config = remoteSelectionCombo
							.getSelectedRemote();
					if (config == null) {
						return null;
					}
					List<URIish> uris = config.getURIs();
					if (uris == null || uris.isEmpty()) {
						return null;
					}
					return uris.get(0).toString();
				}, uri -> {
					FutureRefs list = refs.get(uri);
					if (list == null) {
						list = new FutureRefs(repository, uri);
						refs.put(uri, list);

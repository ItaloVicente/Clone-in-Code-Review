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
					AsynchronousBranchList list = refs.get(uri);
					if (list == null) {
						list = new AsynchronousBranchList(repository, uri,
								null);
						refs.put(uri, list);
					}
					return list;
				});
		candidateProvider.setContentProposalAdapter(
				UIUtils.addRefContentProposalToText(remoteBranchNameText,
						this.repository, candidateProvider, true));

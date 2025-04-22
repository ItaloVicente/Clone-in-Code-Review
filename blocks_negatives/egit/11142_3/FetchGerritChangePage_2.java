								RevCommit commit = fetchChange(uri, spec,
										monitor);

								if (doCreateTag) {
									createTag(spec, textForTag, commit, monitor);
								}
								if (doCreateBranch) {
									createBranch(textForBranch, commit, monitor);
								}
								if (doCheckout || doCreateTag) {
									checkout(commit, monitor);
								}
								if (doActivateAdditionalRefs) {
									activateAdditionalRefs();
								}
								storeLastUsedUri(uri);

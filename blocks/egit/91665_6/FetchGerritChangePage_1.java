						RevCommit commit = fetchChange(uri, spec, monitor);
						switch (mode) {
						case CHECKOUT_FETCH_HEAD:
							checkout(commit.name(), monitor);
							break;
						case CREATE_TAG:
							createTag(spec, textForTag, commit, monitor);
							checkout(commit.name(), monitor);
							break;
						case CREATE_BRANCH:
							createBranch(textForBranch, doCheckout,
									commit, monitor);
							break;
						default:
						}
						if (doActivateAdditionalRefs) {
							activateAdditionalRefs();
						}
						storeLastUsedUri(uri);
					} finally {
						monitor.done();

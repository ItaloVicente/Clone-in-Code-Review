						switch (mode) {
						case CHANGE_BRANCH:
							Ref localRef = getLocalRef(spec.getSource());
							checkout(localRef.getName(), monitor);
							break;
						case CHECKOUT_FETCH_HEAD: {
							RevCommit commit = fetchChange(uri, spec, monitor);
							checkout(commit.name(), monitor);
							break;
						}
						case CREATE_TAG: {
							RevCommit commit = fetchChange(uri, spec, monitor);
							createTag(spec, textForTag, commit, monitor);
							checkout(commit.name(), monitor);
							break;
						}
						case CREATE_BRANCH: {
							RevCommit commit = fetchChange(uri, spec, monitor);
							createBranch(textForBranch, doCheckoutNewBranch,
									commit,
									monitor);
							break;
						}
						default:
						}
						if (doActivateAdditionalRefs) {
							activateAdditionalRefs();
						}
						storeLastUsedUri(uri);
					} finally {
						monitor.done();

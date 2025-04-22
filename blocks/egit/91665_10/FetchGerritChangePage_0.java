			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				try {
					SubMonitor progress = SubMonitor.convert(monitor,
							UIText.FetchGerritChangePage_GetChangeTaskName,
							getTotalWork(mode));
					RevCommit commit = fetchChange(uri, spec,
							progress.newChild(1));
					switch (mode) {
					case CHECKOUT_FETCH_HEAD:
						checkout(commit.name(), progress.newChild(1));
						break;
					case CREATE_TAG:
						createTag(spec, textForTag, commit,
								progress.newChild(1));
						checkout(commit.name(), progress.newChild(1));
						break;
					case CREATE_BRANCH:
						createBranch(textForBranch, doCheckoutNewBranch, commit,
								progress.newChild(1));
						break;
					default:
					}
					if (doActivateAdditionalRefs) {
						activateAdditionalRefs();

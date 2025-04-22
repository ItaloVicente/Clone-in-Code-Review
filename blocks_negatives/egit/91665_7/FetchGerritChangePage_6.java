				if (doCreateTag)
					createTag(spec, textForTag, commit, monitor);

				if (doCreateBranch)
					createBranch(textForBranch, doCheckoutNewBranch, commit,
							monitor);

				if (doCheckout || doCreateTag)
					checkout(commit.name(), monitor);

				if (doActivateAdditionalRefs)
					activateAdditionalRefs();

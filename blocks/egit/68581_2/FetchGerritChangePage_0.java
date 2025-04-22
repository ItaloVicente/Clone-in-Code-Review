					try {
						internalDoFetch(spec, uri, doCheckout, doCreateTag,
								doCreateBranch, doCheckoutNewBranch,
								doActivateAdditionalRefs, textForTag,
								textForBranch, monitor);
					} catch (Exception e) {
						try {
							throw e;
						} catch (Exception e1) {
							Activator.handleError(e.getMessage(), e1, true);
						}
					}

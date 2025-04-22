					try {
						internalDoFetch(spec, uri, doCheckout, doCreateTag,
								doCreateBranch, doCheckoutNewBranch,
								doActivateAdditionalRefs, textForTag,
								textForBranch, monitor);
					} catch (CoreException ce) {
						return ce.getStatus();
					} catch (Exception e) {
						return Activator.createErrorStatus(e.getLocalizedMessage(), e);
					}

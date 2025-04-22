					try {
						internalDoFetch(spec, uri, doCheckout, doCreateTag,
								doCreateBranch, doCheckoutNewBranch,
								doActivateAdditionalRefs, textForTag,
								textForBranch, monitor);
					} catch (Exception e) {
						setProperty(
								IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
								Boolean.TRUE);
						return Activator.createErrorStatus(e.getLocalizedMessage(), e);
					}

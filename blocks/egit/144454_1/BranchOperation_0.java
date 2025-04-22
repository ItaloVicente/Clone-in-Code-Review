						String msg = MessageFormat.format(
								CoreText.BranchOperation_checkoutError,
								target, repo.getDirectory());
						if (logErrors) {
							Activator.logError(msg, e);
						} else {
							throw new CoreException(Activator.error(msg, e));
						}

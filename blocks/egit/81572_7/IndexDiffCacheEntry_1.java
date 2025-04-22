				if (event.getDelta() != null) {
					SkipNotInterestingDeltaVisitor skipNotInterestingVisitor = new SkipNotInterestingDeltaVisitor();
					try {
						event.getDelta().accept(skipNotInterestingVisitor);
						if (!skipNotInterestingVisitor
								.hasAtLeastOneInterestingDelta()) {
							return;
						}
					} catch (CoreException e) {
						Activator.logError(e.getMessage(), e);
					}
				}


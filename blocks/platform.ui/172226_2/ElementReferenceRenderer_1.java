						if (theContext != null) {
							if (theContext.getParent() == curContext) {
								if (curContext.getActiveChild() == theContext) {
									theContext.deactivate();
								}
								theContext.setParent(newParentContext);

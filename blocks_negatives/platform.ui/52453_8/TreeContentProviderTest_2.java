				if (element instanceof SimpleNode) {
					return Observables
							.proxyObservableSet(((SimpleNode) element)
									.getChildren());
				}

				return null;

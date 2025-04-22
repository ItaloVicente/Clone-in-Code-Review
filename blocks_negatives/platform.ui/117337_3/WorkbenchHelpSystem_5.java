			} else if (object instanceof IContextComputer) {
				Object[] helpContexts = ((IContextComputer) object)
						.computeContexts(event);
				if (helpContexts != null && helpContexts.length > 0) {
					Object primaryEntry = helpContexts[0];
					if (primaryEntry instanceof String) {
						context = HelpSystem.getContext((String) primaryEntry);
					} else if (primaryEntry instanceof IContext) {
						context = (IContext) primaryEntry;
					}
				}

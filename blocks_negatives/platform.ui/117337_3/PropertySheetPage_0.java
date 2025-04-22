				if (helpContext instanceof IContextComputer) {
					contexts= ((IContextComputer)helpContext)
				            .getLocalContexts(e);
				} else {
					contexts= (Object[])helpContext;
				}

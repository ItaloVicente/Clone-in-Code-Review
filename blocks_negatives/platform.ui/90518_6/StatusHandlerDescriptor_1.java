			for (int i = 0; i < parameters.length; i++) {
				params
						.put(
								parameters[i]
										.getAttribute(IWorkbenchRegistryConstants.ATT_NAME),
								parameters[i]
										.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE));

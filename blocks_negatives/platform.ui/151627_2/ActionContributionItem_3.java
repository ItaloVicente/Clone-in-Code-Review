				if (accelerator == 0) {
					if ((callback != null) && (commandId != null)) {
						acceleratorText = callback.getAcceleratorText(commandId);
					}
				}

				IContributionManagerOverrides overrides = null;

				if (getParent() != null) {
					overrides = getParent().getOverrides();
				}

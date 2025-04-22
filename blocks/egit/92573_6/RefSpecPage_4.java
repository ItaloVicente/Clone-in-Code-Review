
				recurseSubmodulesYesButton.setSelection(false);
				recurseSubmodulesNoButton.setSelection(false);
				recurseSubmodulesOnDemandButton.setSelection(false);

				final FetchRecurseSubmodulesMode recurse = local.getConfig()
						.getEnum(ConfigConstants.CONFIG_FETCH_SECTION, null,
								ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES,
								FetchRecurseSubmodulesMode.ON_DEMAND);
				switch (recurse) {
				case YES:
					recurseSubmodulesYesButton.setSelection(true);
					break;
				case NO:
					recurseSubmodulesNoButton.setSelection(true);
					break;
				case ON_DEMAND:
					recurseSubmodulesOnDemandButton.setSelection(true);
					break;
				}

						if (!filterActionProvider(providerDesciptorLocal)) {
							CommonActionProvider provider = null;
							provider = getActionProviderInstance(providerDesciptorLocal);
							provider.setContext(actionContextLocal);
							provider.fillActionBars(theActionBars);
							provider.updateActionBars();
						}

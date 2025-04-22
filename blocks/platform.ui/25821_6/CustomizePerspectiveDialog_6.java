				} else if (contributionItem instanceof ActionSetContributionItem) {
					ActionSet actionSet = idToActionSet
							.get(((ActionSetContributionItem) contributionItem).getActionSetId());
					ActionSetDescriptor descriptor = actionSet.descriptor;
					System.out.println(descriptor.getLabel());
					PluginActionSet plugActionSet = null;
					try {
						plugActionSet = (PluginActionSet) descriptor.createActionSet();
						System.out.println(plugActionSet.toString());
					} catch (CoreException ex) {
						WorkbenchPlugin
								.log("Unable to create action set " + descriptor.getId(), ex); //$NON-NLS-1$
					}

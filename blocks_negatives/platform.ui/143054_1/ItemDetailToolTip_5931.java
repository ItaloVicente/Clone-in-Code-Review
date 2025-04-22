						ActionSet actionSet = actionGroup.
								iterator().next();
						text = NLS.bind(
								WorkbenchMessages.HideItems_unavailableChildCommandGroup,
								actionSet.descriptor.getId(),
								actionSet.descriptor.getLabel());

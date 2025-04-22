				} else {
					if (extensionDelta.getKind() == IExtensionDelta.REMOVED) {
						if (id.equals(IWorkbenchRegistryConstants.PL_ACTION_SET_PART_ASSOCIATIONS)) {
							revokeList.add(0, extensionDelta);
							numActionSetPartAssoc++;
						} else if (id.equals(IWorkbenchRegistryConstants.PL_PERSPECTIVES)) {

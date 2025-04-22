				} else if (extensionDelta.getKind() == IExtensionDelta.REMOVED) {
					switch (id) {
					case IWorkbenchRegistryConstants.PL_ACTION_SET_PART_ASSOCIATIONS:
						revokeList.add(0, extensionDelta);
						numActionSetPartAssoc++;
						break;
					case IWorkbenchRegistryConstants.PL_PERSPECTIVES:
						revokeList.add(numActionSetPartAssoc, extensionDelta);
						break;
					default:
						revokeList.add(extensionDelta);
						break;

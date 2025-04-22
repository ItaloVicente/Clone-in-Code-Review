					if (child.getName().equals(IWorkbenchRegistryConstants.TAG_PART)) {
						String partId = child.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
						if (partId != null) {
							Object trackingObject = addAssociation(actionSetId, partId);
							if (trackingObject != null) {
								tracker.registerObject(extension, trackingObject, IExtensionTracker.REF_STRONG);

							}

						}
					} else {
						WorkbenchPlugin.log("Unable to process element: " + //$NON-NLS-1$
								child.getName() + " in action set part associations extension: " + //$NON-NLS-1$
								extension.getUniqueIdentifier());
					}
				}
			}
		}

		mapPartToActionSets.clear();
	}

	private void addActionSets(IExtensionTracker tracker, IExtension extension) {

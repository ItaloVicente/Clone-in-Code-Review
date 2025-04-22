			if (element.getName().equals(IWorkbenchRegistryConstants.TAG_ACTION_SET)) {
				try {
					ActionSetDescriptor desc = new ActionSetDescriptor(element);
					addActionSet(desc);
					tracker.registerObject(extension, desc, IExtensionTracker.REF_WEAK);

				} catch (CoreException e) {
					WorkbenchPlugin.log("Unable to create action set descriptor.", e.getStatus());//$NON-NLS-1$
				}
			}
		}

		mapPartToActionSets.clear();
	}

	@Override

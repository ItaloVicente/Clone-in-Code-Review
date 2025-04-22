			}
		} else {
			String state = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_STATE);
			if (state != null) {
				action.setChecked(state.equals("true"));//$NON-NLS-1$
			}
		}

		String extendingPluginId = actionElement.getDeclaringExtension().getContributor().getName();

		if (icon != null) {
			action.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId, icon));
		}
		if (hoverIcon != null) {
			action.setHoverImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId, hoverIcon));
		}
		if (disabledIcon != null) {
			action.setDisabledImageDescriptor(
					AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId, disabledIcon));
		}

		if (accelerator != null) {

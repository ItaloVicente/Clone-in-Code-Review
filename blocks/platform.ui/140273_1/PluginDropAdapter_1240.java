		try {
			if (PluginTransfer.getInstance().isSupportedType(event.currentDataType)) {
				PluginTransferData pluginData = (PluginTransferData) event.data;
				IDropActionDelegate delegate = getPluginAdapter(pluginData);
				if (!delegate.run(pluginData.getData(), getCurrentTarget())) {
					event.detail = DND.DROP_NONE;
				}
			} else {
				super.drop(event);
			}
		} catch (CoreException e) {
			WorkbenchPlugin.log("Drop Failed", e.getStatus());//$NON-NLS-1$
		}
	}

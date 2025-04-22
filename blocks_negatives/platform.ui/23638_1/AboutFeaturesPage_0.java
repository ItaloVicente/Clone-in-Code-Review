		Bundle[] bundles = bundleGroup == null ? new Bundle[0] : bundleGroup
				.getBundles();

		AboutPluginsDialog d = new AboutPluginsDialog(
				getShell(),
				getProductName(),
				bundles,
				WorkbenchMessages.AboutFeaturesDialog_pluginInfoTitle,
				NLS
						.bind(
								WorkbenchMessages.AboutFeaturesDialog_pluginInfoMessage,
								bundleGroup.getIdentifier()),

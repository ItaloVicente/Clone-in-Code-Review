		if (property == null) {
			if (!Platform.inDevelopmentMode()) {
				WorkbenchPlugin.log(NLS.bind(WorkbenchMessages.Workbench_missingPropertyMessage, PROP_VM));
			}
			return null;
		}

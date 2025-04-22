		if (id != null) {
			setId(id);
		} else {
			setId("PluginAction." + Integer.toString(actionCount)); //$NON-NLS-1$
			++actionCount;
		}

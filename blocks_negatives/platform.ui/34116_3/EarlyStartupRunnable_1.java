
        String classname = element.getAttribute(IWorkbenchConstants.TAG_CLASS);

        if (classname == null || classname.length() <= 0) {
			return getPluginForCompatibility();
		}


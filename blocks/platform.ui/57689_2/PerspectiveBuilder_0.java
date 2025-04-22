	private void addShowInTags() {
		String origId = null;
		if (perspReader.isCustom()) {
			origId = perspReader.getBasicPerspectiveId();
		} else {
			origId = perspReader.getId();
		}
		ArrayList<String> list = getShowInPartFromRegistry(origId);
		if (list != null) {
			for (String showIn : list) {
				tags.add(ModeledPageLayout.SHOW_IN_PART_TAG + showIn);
			}
		}
		return;
	}

	public static ArrayList<String> getShowInPartFromRegistry(String targetId) {
		ArrayList<String> list = new ArrayList<>();
		IExtension[] extensions = getPerspectiveExtensions();
		if (extensions != null) {
			for (int i = 0; i < extensions.length; i++) {
				list.addAll(getExtensionShowInPartFromRegistry(extensions[i], targetId));
			}
		}
		return list;
	}

	private static IExtension[] getPerspectiveExtensions() {
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_PERSPECTIVE_EXTENSIONS);
        if (point == null) {
			return null;
		}
		IExtension[] extensions = point.getExtensions();
        extensions = RegistryReader.orderExtensions(extensions);
		return extensions;
	}

	private static ArrayList<String> getExtensionShowInPartFromRegistry(IExtension extension, String targetId) {
		ArrayList<String> list = new ArrayList<>();
		IConfigurationElement[] configElements = extension.getConfigurationElements();
		for (int j = 0; j < configElements.length; j++) {
			String type = configElements[j].getName();
			if (type.equals(IWorkbenchRegistryConstants.TAG_PERSPECTIVE_EXTENSION)) {
				String id = configElements[j].getAttribute(IWorkbenchRegistryConstants.ATT_TARGET_ID);
				if (targetId.equals(id) || "*".equals(id)) { //$NON-NLS-1$
					list.addAll(getConfigElementShowInPartsFromRegistry(configElements[j]));
				}
			}
		}
		return list;
	}

	private static ArrayList<String> getConfigElementShowInPartsFromRegistry(IConfigurationElement configElement) {
		ArrayList<String> list = new ArrayList<>();
		String tag = IWorkbenchRegistryConstants.TAG_SHOW_IN_PART;
		IConfigurationElement[] children = configElement.getChildren();
		for (int nX = 0; nX < children.length; nX++) {
			IConfigurationElement child = children[nX];
			String ctype = child.getName();
			if (tag.equals(ctype)) {
				String tid = child.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
				if (tid != null) {
					list.add(tid);
				}
			}
		}
		return list;
	}


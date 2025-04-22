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
		String tag = IWorkbenchRegistryConstants.TAG_SHOW_IN_PART;
		IExtensionPoint point = Platform.getExtensionRegistry().getExtensionPoint(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_PERSPECTIVE_EXTENSIONS);
        if (point == null) {
			return null;
		}
		ArrayList<String> list = new ArrayList<>();
        IExtension[] extensions = point.getExtensions();
        extensions = RegistryReader.orderExtensions(extensions);
        for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] configElements = extensions[i].getConfigurationElements();

			for (int j = 0; j < configElements.length; j++) {
				String type = configElements[j].getName();
				if (type.equals(IWorkbenchRegistryConstants.TAG_PERSPECTIVE_EXTENSION)) {
		            String id = configElements[j].getAttribute(IWorkbenchRegistryConstants.ATT_TARGET_ID);
					if (targetId.equals(id) || "*".equals(id)) { //$NON-NLS-1$
		            	IConfigurationElement[] children = configElements[j].getChildren();
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
		            }
		        }
			}
		}
		return list;
    }


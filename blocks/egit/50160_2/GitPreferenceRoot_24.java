	private static void loadExternalToolAttributes(FileBasedConfig config,
			String sectionName,
			String subSectionName, BaseToolManager manager,
			String[][] attributes, boolean useDefault) {
		for (String[] attr : attributes) {
			String attrName = attr[0];
			String attrDefValue = attr[1];
			String attrValue = config.getString(sectionName, subSectionName, attrName);
			if (attrValue != null) {
				manager.addAttribute(subSectionName, attrName, attrValue);
				System.out
						.println("addAttribute: FOUND: " + subSectionName + ", " //$NON-NLS-1$ //$NON-NLS-2$
						+ attrName + ", " + attrValue); //$NON-NLS-1$
			} else if (useDefault && attrDefValue != null) {
				manager.addAttribute(subSectionName, attrName, attrDefValue);
				System.out.println(
						"addAttribute: DEFAULT: " + subSectionName + ", " //$NON-NLS-1$ //$NON-NLS-2$
								+ attrName + ", " + attrDefValue); //$NON-NLS-1$

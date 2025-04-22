		setSectionExpanded(diffSection, diffs.length != 0);
	}

	static void setSectionExpanded(Section section, boolean expanded) {
		section.setExpanded(expanded);
		((GridData) section.getLayoutData()).grabExcessVerticalSpace = expanded;

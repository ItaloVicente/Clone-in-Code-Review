		return partName;
	}

	protected void setPartName(String partName) {

		internalSetPartName(partName);

		setDefaultTitle();
	}

	void setDefaultTitle() {
		String description = getContentDescription();
		String name = getPartName();
		String newTitle = name;

		if (!Util.equals(description, "")) { //$NON-NLS-1$
			newTitle = MessageFormat.format(WorkbenchMessages.WorkbenchPart_AutoTitleFormat, name, description);
		}

		setTitle(newTitle);
	}

	@Override

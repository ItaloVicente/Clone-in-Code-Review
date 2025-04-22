		Assert.assertNotNull(menuMgr.find(ADD_ACTION_ID));
	}

	public void addStandardItems() {
		addElement(new ListElement("red"));
		addElement(new ListElement("blue"));
		addElement(new ListElement("green"));
		addElement(new ListElement("red", true));
	}

	private boolean useStaticMenu() {
		Object data = getData();
		if (data instanceof String) {
			String arg = (String) data;
			return arg.contains("-staticMenu"); //$NON-NLS-1$
		}
		return false;
	}

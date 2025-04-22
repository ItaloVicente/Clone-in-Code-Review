		super.editorContextMenuAboutToShow(parentMenu);
		parentMenu.add(new Separator());
		IMenuManager subMenu = new MenuManager(MessageUtil.getString("Add")); //$NON-NLS-1$
		parentMenu.add(subMenu);
		if (subMenu != null) {
			Object[][] att = new Object[][] { { IReadmeConstants.MARKER_ATT_ID, Integer.valueOf(1234) } };
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "1", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + " id=1234")); //$NON-NLS-1$ //$NON-NLS-2$

			att = new Object[][] { { IReadmeConstants.MARKER_ATT_LEVEL, Integer.valueOf(7) } };
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "2", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + " level=7")); //$NON-NLS-1$ //$NON-NLS-2$

			att = new Object[][] { { IReadmeConstants.MARKER_ATT_LEVEL, Integer.valueOf(7) },
					{ IReadmeConstants.MARKER_ATT_DEPT, "infra" } }; //$NON-NLS-1$
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "3", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + " level=7, department=infra")); //$NON-NLS-1$ //$NON-NLS-2$

			att = new Object[][] { { IReadmeConstants.MARKER_ATT_CODE, "red" } }; //$NON-NLS-1$
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "4", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + " code=red")); //$NON-NLS-1$ //$NON-NLS-2$

			att = new Object[][] { { IReadmeConstants.MARKER_ATT_LANG, "english" } }; //$NON-NLS-1$
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "5", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + " language=english")); //$NON-NLS-1$ //$NON-NLS-2$

			att = new Object[][] { { IReadmeConstants.MARKER_ATT_ID, Integer.valueOf(1234) },
					{ IReadmeConstants.MARKER_ATT_LEVEL, Integer.valueOf(7) },
					{ IReadmeConstants.MARKER_ATT_DEPT, "infra" }, //$NON-NLS-1$
					{ IReadmeConstants.MARKER_ATT_CODE, "red" }, //$NON-NLS-1$
					{ IReadmeConstants.MARKER_ATT_LANG, "english" } }; //$NON-NLS-1$
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "6", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + //$NON-NLS-1$
							" id=1234, level=7, department=infra, code=red, language=english")); //$NON-NLS-1$

			att = new Object[0][0];
			subMenu.add(new AddReadmeMarkerAction(this, MessageUtil.getString("Add_readme_marker_action_label") + "7", //$NON-NLS-1$ //$NON-NLS-2$
					att, MessageUtil.getString("Readme_marker_message_example") + " No attributes specified")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

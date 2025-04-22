	}

	private PluginAction createAction(int targetType, IConfigurationElement actionElement, Object target,
			String style) {
		int actionStyle = IAction.AS_UNSPECIFIED;
		if (style != null) {
			if (style.equals(STYLE_RADIO)) {
				actionStyle = IAction.AS_RADIO_BUTTON;
			} else if (style.equals(STYLE_TOGGLE)) {
				actionStyle = IAction.AS_CHECK_BOX;
			} else if (style.equals(STYLE_PULLDOWN)) {
				actionStyle = IAction.AS_DROP_DOWN_MENU;
			} else if (style.equals(STYLE_PUSH)) {
				actionStyle = IAction.AS_PUSH_BUTTON;
			}
		}

		switch (targetType) {
		case T_VIEW:
			return new ViewPluginAction(actionElement, (IViewPart) target, id, actionStyle);
		case T_EDITOR:
			return new EditorPluginAction(actionElement, (IEditorPart) target, id, actionStyle);
		case T_WORKBENCH:
			return new WWinPluginAction(actionElement, (IWorkbenchWindow) target, id, actionStyle);
		case T_WORKBENCH_PULLDOWN:
			actionStyle = IAction.AS_DROP_DOWN_MENU;
			return new WWinPluginPulldown(actionElement, (IWorkbenchWindow) target, id, actionStyle);
		case T_POPUP:
			return new ObjectPluginAction(actionElement, id, actionStyle);
		default:
			WorkbenchPlugin.log("Unknown Action Type: " + targetType);//$NON-NLS-1$
			return null;
		}
	}

	public PluginAction getAction() {
		return action;
	}

	public String getId() {
		return id;
	}

	public String getMenuGroup() {
		return menuGroup;
	}

	public String getMenuPath() {
		return menuPath;
	}

	public String getToolbarGroupId() {
		return toolbarGroupId;
	}

	public String getToolbarId() {
		return toolbarId;
	}

	@Override

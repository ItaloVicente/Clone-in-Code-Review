    }

    /**
     * Creates an instance of PluginAction. Depending on the target part,
     * subclasses of this class may be created.
     */
    private PluginAction createAction(int targetType,
            IConfigurationElement actionElement, Object target, String style) {
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
            return new ViewPluginAction(actionElement, (IViewPart) target, id,
                    actionStyle);
        case T_EDITOR:
            return new EditorPluginAction(actionElement, (IEditorPart) target,
                    id, actionStyle);
        case T_WORKBENCH:
            return new WWinPluginAction(actionElement,
                    (IWorkbenchWindow) target, id, actionStyle);
        case T_WORKBENCH_PULLDOWN:
            actionStyle = IAction.AS_DROP_DOWN_MENU;
            return new WWinPluginPulldown(actionElement,
                    (IWorkbenchWindow) target, id, actionStyle);
        case T_POPUP:
            return new ObjectPluginAction(actionElement, id, actionStyle);
        default:
            return null;
        }
    }

    /**
     * Returns the action object held in this descriptor.
     *
     * @return the action
     */
    public PluginAction getAction() {
        return action;
    }

    /**
     * Returns action's id as defined in the registry.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Returns named slot (group) in the menu where this action
     * should be added.
     *
     * @return the menu group
     */
    public String getMenuGroup() {
        return menuGroup;
    }

    /**
     * Returns menu path where this action should be added. If null,
     * the action will not be added into the menu.
     *
     * @return the menubar path
     */
    public String getMenuPath() {
        return menuPath;
    }

    /**
     * Returns the named slot (group) in the tool bar where this
     * action should be added.
     *
     * @return the toolbar group id
     */
    public String getToolbarGroupId() {
        return toolbarGroupId;
    }

    /**
     * Returns id of the tool bar where this action should be added.
     * If null, action will not be added to the tool bar.
     *
     * @return the toolbar id
     */
    public String getToolbarId() {
        return toolbarId;
    }

    /**
     * For debugging only.
     */
    @Override

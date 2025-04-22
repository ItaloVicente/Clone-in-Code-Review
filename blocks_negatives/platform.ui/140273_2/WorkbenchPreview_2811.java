        this.theme = currentTheme;
        folder = new CTabFolder(parent, SWT.BORDER);
        folder.setUnselectedCloseVisible(false);
        folder.setEnabled(false);
        folder.setMaximizeVisible(true);
        folder.setMinimizeVisible(true);

        viewForm = new ViewForm(folder, SWT.NONE);
        viewForm.marginHeight = 0;
        viewForm.marginWidth = 0;
        viewForm.verticalSpacing = 0;
        viewForm.setBorderVisible(false);
        toolBar = new ToolBar(viewForm, SWT.FLAT | SWT.WRAP);
        ToolItem toolItem = new ToolItem(toolBar, SWT.PUSH);

        Image hoverImage = WorkbenchImages
                .getImage(IWorkbenchGraphicConstants.IMG_LCL_VIEW_MENU);
        toolItem.setImage(hoverImage);

        viewForm.setTopRight(toolBar);

        viewMessage = new CLabel(viewForm, SWT.NONE);
        viewForm.setTopLeft(viewMessage);

        CTabItem item = new CTabItem(folder, SWT.CLOSE);
        Label text = new Label(viewForm, SWT.NONE);
        viewForm.setContent(text);
        item = new CTabItem(folder, SWT.CLOSE);
        item.setControl(viewForm);
        item.setImage(WorkbenchImages.getImage(ISharedImages.IMG_TOOL_COPY));

        folder.setSelection(item);

        item = new CTabItem(folder, SWT.CLOSE);
        item = new CTabItem(folder, SWT.CLOSE);

        currentTheme.addPropertyChangeListener(fontAndColorListener);
        setColorsAndFonts();
        setTabPosition();
        setTabStyle();
    }

    /**
     * Set the tab style from preferences.
     */
    protected void setTabStyle() {
        boolean traditionalTab = PlatformUI.getPreferenceStore()
                .getBoolean(IWorkbenchPreferenceConstants.SHOW_TRADITIONAL_STYLE_TABS);
        folder.setSimple(traditionalTab);
    }

    /**
     * Set the tab location from preferences.
     */
    protected void setTabPosition() {
        tabPos = PlatformUI.getPreferenceStore().getInt(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION);
        folder.setTabPosition(tabPos);
    }

    /**
     * Set the folder colors and fonts
     */
    private void setColorsAndFonts() {
        folder.setSelectionForeground(theme.getColorRegistry().get(
                IWorkbenchThemeConstants.ACTIVE_TAB_TEXT_COLOR));
        folder.setForeground(theme.getColorRegistry().get(
                IWorkbenchThemeConstants.INACTIVE_TAB_TEXT_COLOR));

        Color[] colors = new Color[2];
        colors[0] = theme.getColorRegistry().get(
                IWorkbenchThemeConstants.INACTIVE_TAB_BG_START);
        colors[1] = theme.getColorRegistry().get(
                IWorkbenchThemeConstants.INACTIVE_TAB_BG_END);
        colors[0] = theme.getColorRegistry().get(
                IWorkbenchThemeConstants.ACTIVE_TAB_BG_START);
        colors[1] = theme.getColorRegistry().get(
                IWorkbenchThemeConstants.ACTIVE_TAB_BG_END);
        folder.setSelectionBackground(colors, new int[] { theme
                .getInt(IWorkbenchThemeConstants.ACTIVE_TAB_PERCENT) }, theme
                .getBoolean(IWorkbenchThemeConstants.ACTIVE_TAB_VERTICAL));

        folder.setFont(theme.getFontRegistry().get(
                IWorkbenchThemeConstants.TAB_TEXT_FONT));
    }

    @Override

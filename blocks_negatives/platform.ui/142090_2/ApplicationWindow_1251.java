            Rectangle clientArea = composite.getClientArea();

            Control[] ws = composite.getChildren();


            for (Control w : ws) {
                    Point e = w.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                            flushCache);
                    w.setBounds(clientArea.x, clientArea.y, clientArea.width,
                            e.y);
                    clientArea.y += e.y;
                    clientArea.height -= e.y;
                } else if (getToolBarControl() == w) {
                    if (toolBarChildrenExist()) {
                        Point e = w.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                                flushCache);
                        w.setBounds(clientArea.x, clientArea.y,
                                clientArea.width, e.y);
                        clientArea.y += e.y + VGAP;
                        clientArea.height -= e.y + VGAP;
                    }
                } else if (getCoolBarControl() == w) {
                    if (coolBarChildrenExist()) {
                        Point e = w.computeSize(clientArea.width, SWT.DEFAULT,
                                flushCache);
                        w.setBounds(clientArea.x, clientArea.y,
                                clientArea.width, e.y);
                        clientArea.y += e.y + VGAP;
                        clientArea.height -= e.y + VGAP;
                    }
                } else if (statusLineManager != null
                        && statusLineManager.getControl() == w) {
                    Point e = w.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                            flushCache);
                    w.setBounds(clientArea.x, clientArea.y + clientArea.height
                            - e.y, clientArea.width, e.y);
                    clientArea.height -= e.y + VGAP;
                } else {
                    w.setBounds(clientArea.x, clientArea.y + VGAP,
                            clientArea.width, clientArea.height - VGAP);
                }
            }
        }
    }

    /**
     * Return the top seperator.
     * @return Label
     */
    protected Label getSeperator1() {
        return seperator1;
    }

    /**
     * Create an application window instance, whose shell will be created under the
     * given parent shell.
     * Note that the window will have no visual representation (no widgets)
     * until it is told to open. By default, <code>open</code> does not block.
     *
     * @param parentShell the parent shell, or <code>null</code> to create a top-level shell
     */
    public ApplicationWindow(Shell parentShell) {
        super(parentShell);
    }

    /**
     * Configures this window to have a menu bar.
     * Does nothing if it already has one.
     * This method must be called before this window's shell is created.
     */
    protected void addMenuBar() {
        if ((getShell() == null) && (menuBarManager == null)) {
            menuBarManager = createMenuManager();
        }
    }

    /**
     * Configures this window to have a status line.
     * Does nothing if it already has one.
     * This method must be called before this window's shell is created.
     */
    protected void addStatusLine() {
        if ((getShell() == null) && (statusLineManager == null)) {
            statusLineManager = createStatusLineManager();
        }
    }

    /**
     * Configures this window to have a tool bar.
     * Does nothing if it already has one.
     * This method must be called before this window's shell is created.
     * @param style swt style bits used to create the Toolbar
     * @see ToolBarManager#ToolBarManager(int)
     * @see ToolBar for style bits
     */
    protected void addToolBar(int style) {
        if ((getShell() == null) && (toolBarManager == null)
                && (coolBarManager == null)) {
            toolBarManager = createToolBarManager2(style);
        }
    }

    /**
     * Configures this window to have a cool bar.
     * Does nothing if it already has one.
     * This method must be called before this window's shell is created.
     *
     * @param style the cool bar style
     * @since 3.0
     */
    protected void addCoolBar(int style) {
        if ((getShell() == null) && (toolBarManager == null)
                && (coolBarManager == null)) {
            coolBarManager = createCoolBarManager2(style);
        }
    }

    @Override

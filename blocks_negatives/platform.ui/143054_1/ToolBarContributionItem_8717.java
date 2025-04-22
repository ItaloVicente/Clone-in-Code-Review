                    ToolBar innerToolBar = toolBarManager.getControl();
                    if (innerToolBar != null) {
                        innerToolBar.setMenu(null);
                        Menu innerParentMenu = innerToolBar.getParent()
                                .getMenu();
                        if (innerParentMenu != null) {
                            innerParentMenu.removeListener(SWT.Hide, this);
                        }
                    }
                }
            });
        }
    }

    /**
     * Handles the disposal of the widget.
     *
     * @param event
     *            the event object
     */
    private void handleWidgetDispose(DisposeEvent event) {
        coolItem = null;
    }

    /**
     * A contribution item is visible iff its internal state is visible <em>or</em>
     * the tool bar manager contains something other than group markers and
     * separators.
     *
     * @return <code>true</code> if the tool bar manager contains something
     *         other than group marks and separators, and the internal state is
     *         set to be visible.
     */
    @Override

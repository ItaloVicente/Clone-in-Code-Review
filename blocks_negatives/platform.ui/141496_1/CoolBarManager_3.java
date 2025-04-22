        }
        return null;
    }

    /**
     * Return a consistent set of wrap indices. The return value will always
     * include at least one entry and the first entry will always be zero.
     * CoolBar.getWrapIndices() is inconsistent in whether or not it returns an
     * index for the first row.
     *
     * @param wraps
     *            the wrap indicies from the cool bar widget
     * @return the adjusted wrap indicies.
     */
    private int[] getAdjustedWrapIndices(int[] wraps) {
        int[] adjustedWrapIndices;
        if (wraps.length == 0) {
            adjustedWrapIndices = new int[] { 0 };
        } else {
            if (wraps[0] != 0) {
                adjustedWrapIndices = new int[wraps.length + 1];
                adjustedWrapIndices[0] = 0;
                for (int i = 0; i < wraps.length; i++) {
                    adjustedWrapIndices[i + 1] = wraps[i];
                }
            } else {
                adjustedWrapIndices = wraps;
            }
        }
        return adjustedWrapIndices;
    }

    /**
     * Returns the control of the Menu Manager. If the menu manager does not
     * have a control then one is created.
     *
     * @return menu control associated with manager, or null if none
     */
    private Menu getContextMenuControl() {
        if ((contextMenuManager != null) && (coolBar != null)) {
            Menu menuWidget = contextMenuManager.getMenu();
            if ((menuWidget == null) || (menuWidget.isDisposed())) {
                menuWidget = contextMenuManager.createContextMenu(coolBar);
            }
            return menuWidget;
        }
        return null;
    }

    @Override

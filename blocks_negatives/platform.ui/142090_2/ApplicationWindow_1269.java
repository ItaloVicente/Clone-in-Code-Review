                mgr.setCancelEnabled(cancelWasEnabled);
                if (currentFocus != null && !currentFocus.isDisposed()) {
                    currentFocus.forceFocus();
                }
            }
        } finally {
            operationInProgress = false;
        }
    }

    /**
     * Sets or clears the message displayed in this window's status
     * line (if it has one). This method has no effect if the
     * window does not have a status line.
     *
     * @param message the status message, or <code>null</code> to clear it
     */
    public void setStatus(String message) {
        if (statusLineManager != null) {
            statusLineManager.setMessage(message);
        }
    }

    /**
     * Returns whether or not children exist for the Application Window's
     * toolbar control.
     * <p>
     * @return boolean true if children exist, false otherwise
     */
    protected boolean toolBarChildrenExist() {
        Control toolControl = getToolBarControl();
        if (toolControl instanceof ToolBar) {
            return ((ToolBar) toolControl).getItemCount() > 0;
        }
        return false;
    }

    /**
     * Returns whether or not children exist for this application window's
     * cool bar control.
     *
     * @return boolean true if children exist, false otherwise
     * @since 3.0
     */
    protected boolean coolBarChildrenExist() {
        Control coolControl = getCoolBarControl();
        if (coolControl instanceof CoolBar) {
            return ((CoolBar) coolControl).getItemCount() > 0;
        }
        return false;
    }

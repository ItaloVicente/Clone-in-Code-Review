                    setShowDerived(showDerivedButton.getSelection());
                    refresh(true);
                }
            });
            showDerivedButton.setSelection(getShowDerived());
        }

        applyDialogFont(dialogArea);
        return dialogArea;
    }

    /**
     * Returns whether to include a "Show derived resources" checkbox in the dialog.
     * The default is <code>false</code>.
     *
     * @return <code>true</code> to include the checkbox, <code>false</code> to omit
     * @since 3.1
     */
    public boolean getAllowUserToToggleDerived() {
        return allowUserToToggleDerived;
    }

    /**
     * Sets whether to include a "Show derived resources" checkbox in the dialog.
     *
     * @param allow <code>true</code> to include the checkbox, <code>false</code> to omit
     * @since 3.1
     */
    public void setAllowUserToToggleDerived(boolean allow) {
        allowUserToToggleDerived = allow;
    }

    /**
     */
    private void filterResources(boolean force) {
        String oldPattern = force ? null : patternString;
        patternString = adjustPattern();
        if (!force && patternString.equals(oldPattern)) {

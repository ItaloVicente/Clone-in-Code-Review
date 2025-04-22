            if (workingSet != null) {
                setTitleToolTip(NLS.bind(ResourceNavigatorMessages.ResourceNavigator_workingSetInputToolTip, inputToolTip, workingSet.getLabel()));
            } else {
                setTitleToolTip(inputToolTip);
            }
        }
    }

    /**
     * Returns the action group.
     *
     * @return the action group
     */
    protected ResourceNavigatorActionGroup getActionGroup() {
        return actionGroup;
    }

    /**
     * Sets the action group.
     *
     * @param actionGroup the action group
     */
    protected void setActionGroup(ResourceNavigatorActionGroup actionGroup) {
        this.actionGroup = actionGroup;
    }

    @Override

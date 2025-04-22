        if (ab != null) {
            ab.deactivate(forceHide);
        }
        super.deactivateActionBars(forceHide);
    }

    /**
     * Returns the editor action bar contributor for this editor.
     * <p>
     * An action contributor is responsable for the creation of actions.
     * By design, this contributor is used for one or more editors of the same type.
     * Thus, the contributor returned by this method is not owned completely
     * by the editor.  It is shared.
     * </p>
     *
     * @return the editor action bar contributor
     */
    @Override

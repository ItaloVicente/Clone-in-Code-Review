        }
    }

    /**
     * Returns the action for the given perspective id. This is a lazy cache. If
     * the action does not already exist, then it is created. If there is no
     * perspective with the given identifier, then the action is not created.
     *
     * @param id
     *            The identifier of the perspective for which the action should
     *            be retrieved.
     * @return The action for the given identifier; or <code>null</code> if
     *         there is no perspective with the given identifier.
     * @since 3.1
     */
    private final IAction getAction(final String id) {

    /**
     * Open a workspace selection dialog on the argument shell, populating the
     * argument data with the user's selection. Perform first level validation
     * on the selection by comparing the version information. This method does
     * not examine the runtime state (e.g., is the workspace already locked?).
     *
     * @param shell
     * @param launchData
     * @param force
     *            setting to true makes the dialog open regardless of the
     *            showDialog value
     * @return An URL storing the selected workspace or null if the user has
     *         canceled the launch operation.
     */

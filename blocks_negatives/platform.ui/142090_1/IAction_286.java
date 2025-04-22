    /**
     * Property name of an action's success/fail result
     * (value <code>"result"</code>). The values are
     * <code>Boolean.TRUE</code> if running the action succeeded and
     * <code>Boolean.FALSE</code> if running the action failed or did not
     * complete.
     * <p>
     * Not all actions report whether they succeed or fail. This property
     * is provided for use by actions that may be invoked by clients that can
     * take advantage of this information when present (for example, actions
     * used in cheat sheets). Clients should always assume that running the
     * action succeeded in the absence of notification to the contrary.
     * </p>
     *
     * @since 3.0
     */

 * A <code>RetargetAction</code> tracks the active part in the workbench.
 * Each RetargetAction has an ID.  If the active part provides an action
 * handler for the ID the enable and check state of the RetargetAction
 * is determined from the enable and check state of the handler.  If the
 * active part does not provide an action handler then this action is
 * disabled.

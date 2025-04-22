 * message, author, committer, signed off toggle, amend toggle and change id
 * toggle. Controls for commit message, author and committer are created by the
 * component host and attached via method <code>attachControls</code>. The
 * toggles (signed off, amend, change id) are provided by the host and can be of
 * any widget type (check box, tool bar item etc.). The host must notify the
 * commit message component when a toggle state changes by calling the methods
 * <code>setSignedOffButtonSelection</code>,
 * <code>setChangeIdButtonSelection</code> and
 * <code>setAmendingButtonSelection</code>. The component notifies the host via
 * interface {@link ICommitMessageComponentNotifications} about required changes
 * of the toggle selections.

 * beyond a mere {@link IStatus} back to the user via an {@link IAction}. If the
 * job is running in a dialog when its {@link #performJob(IProgressMonitor)}
 * method returns, the action is invoked directly in the display thread,
 * otherwise {@link IProgressConstants#ACTION_PROPERTY} is used to associate the
 * action with the finished job and eventual display of the result is left to
 * the progress reporting framework.

 * <li> When a <code>worked</code> or <code>subtask</code> call occurs the first time,
 *		the progress monitor posts a runnable into the asynchronous SWT event queue.
 * </li>
 * <li> Subsequent calls to <code>worked</code> or <code>subtask</code> do not post
 *		a new runnable as long as a previous runnable still exists in the SWT event
 *		queue. In this case, the progress monitor just updates the internal state of
 *		the runnable that waits in the SWT event queue for its execution. If no runnable
 *		exists, a new one is created and posted into the event queue.

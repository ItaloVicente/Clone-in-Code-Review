 * <p>
 * 1. Call {@link #snapshot()} to get the current projects for the currently
 * checked out branch
 * <p>
 * 2. Call {@link #save(ProjectTrackerMemento)} after the new branch has been
 * successfully checked out with the memento returned from step 1
 * <p>
 * 3. Call {@link #restore(IProgressMonitor)} to restore the projects for the
 * newly checked out branch
 *

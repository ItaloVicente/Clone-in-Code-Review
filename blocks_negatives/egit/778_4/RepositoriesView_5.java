 *
 * The Git Repositories view.
 * <p>
 * This keeps track of a bunch of local directory names each of which represent
 * a Git Repository. This list is stored in some Preferences object and used to
 * build the tree in the view.
 * <p>
 * Implements {@link ISelectionProvider} in order to integrate with the
 * Properties view.
 * <p>
 * This periodically refreshes itself in order to react on Repository changes.

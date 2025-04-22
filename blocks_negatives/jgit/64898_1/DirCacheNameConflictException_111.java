 * Note. Java cannot really handle these as file system objects.
 *
 * @deprecated To look up information about a single path, use
 * {@link org.eclipse.jgit.treewalk.TreeWalk#forPath(Repository, String, org.eclipse.jgit.revwalk.RevTree)}.
 * To lookup information about multiple paths at once, use a
 * {@link org.eclipse.jgit.treewalk.TreeWalk} and obtain the current entry's
 * information from its getter methods.

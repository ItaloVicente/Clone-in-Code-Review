
package org.eclipse.jgit.revwalk;

public abstract class RevAnnotation<V> {
	public abstract V get(RevCommit obj);

	public abstract void set(RevCommit obj
}

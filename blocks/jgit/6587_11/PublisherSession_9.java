
package org.eclipse.jgit.transport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;

public class PublisherReverseResolver {
	private final Map<Object

	public PublisherReverseResolver() {
		repositoryNameLookup = new ConcurrentHashMap<Object
	}

	public void register(Repository r
			throws NotSupportedException {
		repositoryNameLookup.put(getKey(r)
	}

	public String find(Repository r) throws NotSupportedException {
		return repositoryNameLookup.get(getKey(r));
	}

	protected Object getKey(Repository r) throws NotSupportedException {
		if (r.getDirectory() == null)
			throw new NotSupportedException(JGitText
					.get().repositoryMustHaveDirectory);
		return r.getDirectory().getAbsolutePath();
	}
}

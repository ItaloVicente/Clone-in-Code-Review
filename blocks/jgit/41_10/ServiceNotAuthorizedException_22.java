
package org.eclipse.jgit.http.server.resolver;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;

public interface RepositoryResolver {
	Repository open(HttpServletRequest req
			throws RepositoryNotFoundException
}

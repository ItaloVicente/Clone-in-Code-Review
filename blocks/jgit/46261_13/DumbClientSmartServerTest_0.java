package org.eclipse.jgit.http.test;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

public final class TestRepositoryResolver
		implements RepositoryResolver<HttpServletRequest> {

	private final TestRepository<Repository> repo;

	private final String repoName;

	public TestRepositoryResolver(TestRepository<Repository> repo
		this.repo = repo;
		this.repoName = repoName;
	}

	@Override
	public Repository open(HttpServletRequest req
			throws RepositoryNotFoundException
		if (!name.equals(repoName))
			throw new RepositoryNotFoundException(name);

		Repository db = repo.getRepository();
		db.incrementOpen();
		return db;
	}
}

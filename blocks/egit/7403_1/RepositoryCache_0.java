package org.eclipse.egit.core.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;

import java.io.IOException;

import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.jgit.lib.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RepositoryCacheTest extends GitTestCase {

	private TestRepository testRepository;
	private Repository repository;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		testRepository = new TestRepository(gitDir);
		repository = testRepository.getRepository();
	}

	@After
	public void tearDown() throws Exception {
		testRepository.dispose();
		repository = null;
		super.tearDown();
	}

	@Test
	public void shouldNotContainDeletedRepository() throws IOException {
		RepositoryCache cache = Activator.getDefault().getRepositoryCache();
		cache.lookupRepository(repository.getDirectory());
		assertThat(repository, isIn(cache.getAllRepositories()));
		repository.close();
		assertThat(repository, isIn(cache.getAllRepositories()));
	}

}

package org.eclipse.egit.core.internal.merge;

import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.core.test.TestProject;
import org.eclipse.jgit.api.Git;
import org.junit.After;
import org.junit.Before;

public class StrategyRecursiveModelWithDeepProjectTest extends
		StrategyRecursiveModelTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		testRepo.disconnect(iProject);

		project = new TestProject(true, "a/b/deepProject");
		iProject = project.project;
		testRepo.connect(iProject);
		repo = RepositoryMapping.getMapping(iProject).getRepository();

		try (Git git = new Git(repo)) {
			git.commit().setAuthor("JUnit", "junit@jgit.org")
				.setMessage("Initial commit").call();
		}
	}

	@After
	public void clearGitResources() throws Exception {
		testRepo.disconnect(iProject);
		testRepo.dispose();
		repo = null;

		super.tearDown();
	}
}

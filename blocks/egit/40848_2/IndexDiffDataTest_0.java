package org.eclipse.egit.core.internal.indexdiff;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.JobFamilies;
import org.eclipse.egit.core.test.GitTestCase;
import org.eclipse.egit.core.test.TestRepository;
import org.eclipse.jgit.lib.Repository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IndexDiffCacheEntryTest extends GitTestCase {

	private static final int MAX_FILES_FOR_UPDATE = 1;
	private static final int MAX_WAIT_TIME = 60 * 1000;


	private TestRepository testRepository;

	private Repository repository;

	private IndexDiffCacheEntry2 entry;

	@Test
	public void basicTest() throws Exception {
		prepareCacheEntry();

		entry.refresh();
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertFalse(entry.updateScheduled);
		cleanEntryFlags();

		entry.refreshFiles(Arrays.asList("a"));
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertFalse(entry.reloadScheduled);
		assertTrue(entry.updateScheduled);
		cleanEntryFlags();

		entry.refreshFiles(Arrays.asList("a", "b"));
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertTrue(entry.updateScheduled);
		cleanEntryFlags();

		entry.getUpdateJob().addChanges(Arrays.asList("a", "b"),
				Collections.<IResource> emptyList());
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertFalse(entry.updateScheduled);
		cleanEntryFlags();
	}

	@Test
	public void testRealoadAndUpdate() throws Exception {
		prepareCacheEntry();

		testRepository.connect(project.project);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertFalse(entry.reloadScheduled);
		assertFalse(entry.updateScheduled);
		cleanEntryFlags();

		testRepository.addToIndex(project.project);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertTrue(entry.updateScheduled);
		cleanEntryFlags();

		testRepository.createInitialCommit("first commit\n");
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertTrue(entry.updateScheduled);
		cleanEntryFlags();

		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					project.createFile("bla", "bla\n".getBytes("UTF-8"));
					project.createFile("blup", "blup\n".getBytes("UTF-8"));
				} catch (Exception e) {
					throw new CoreException(Activator.error("Failure", e));
				}

			}
		}, null);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertTrue(entry.updateScheduled);
		cleanEntryFlags();

		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					project.createFile("blip", "blip\n".getBytes("UTF-8"));
				} catch (Exception e) {
					throw new CoreException(Activator.error("Failure", e));
				}

			}
		}, null);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertFalse(entry.reloadScheduled);
		assertTrue(entry.updateScheduled);
		cleanEntryFlags();

		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					project.createFile(".gitignore", "\n".getBytes("UTF-8"));
				} catch (Exception e) {
					throw new CoreException(Activator.error("Failure", e));
				}

			}
		}, null);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertFalse(entry.updateScheduled);
		cleanEntryFlags();
	}

	private void waitForJobs(long maxWaitTime, Object family)
			throws InterruptedException {
		Thread.sleep(50);
		long start = System.currentTimeMillis();
		IJobManager jobManager = Job.getJobManager();

		Job[] jobs = jobManager.find(family);
		while (jobs.length > 0) {
			Thread.sleep(100);
			jobs = jobManager.find(family);
			if (System.currentTimeMillis() + maxWaitTime - start < 0) {
				return;
			}
		}
	}

	private void cleanEntryFlags() {
		entry.reloadScheduled = false;
		entry.updateScheduled = false;
	}

	private IndexDiffCacheEntry2 prepareCacheEntry() throws Exception {
		entry = new IndexDiffCacheEntry2(repository);
		waitForJobs(MAX_WAIT_TIME, JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		assertTrue(entry.reloadScheduled);
		assertFalse(entry.updateScheduled);
		cleanEntryFlags();
		return entry;
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		testRepository = new TestRepository(gitDir);
		repository = testRepository.getRepository();
	}

	@After
	public void tearDown() throws Exception {
		entry.dispose();
		testRepository.dispose();
		repository = null;
		super.tearDown();
	}

	class IndexDiffCacheEntry2 extends IndexDiffCacheEntry {


		boolean reloadScheduled;

		boolean updateScheduled;

		public IndexDiffCacheEntry2(Repository repository) {
			super(repository);
		}

		@Override
		protected void scheduleReloadJob(String trigger) {
			reloadScheduled = true;
			System.out.println("Reload: " + trigger);
			super.scheduleReloadJob(trigger);
		}

		@Override
		protected void scheduleUpdateJob(Collection<String> filesToUpdate,
				Collection<IResource> resourcesToUpdate) {
			updateScheduled = true;
			System.out.println("Update: " + filesToUpdate);
			super.scheduleUpdateJob(filesToUpdate, resourcesToUpdate);
		}

		@Override
		protected boolean shouldReload(Collection<String> filesToUpdate) {
			return filesToUpdate.size() > MAX_FILES_FOR_UPDATE;
		}

		public IndexDiffUpdateJob getUpdateJob() {
			return super.getUpdateJob();
		}
	}

}

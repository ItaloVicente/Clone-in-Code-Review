package org.eclipse.jgit.niofs.internal.manager;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.niofs.internal.AbstractTestInfra;
import org.eclipse.jgit.niofs.internal.JGitFileSystem;
import org.eclipse.jgit.niofs.internal.JGitFileSystemImpl;
import org.eclipse.jgit.niofs.internal.JGitFileSystemLock;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProvider;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration;
import org.eclipse.jgit.niofs.internal.JGitFileSystemProxy;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JGitFileSystemsCacheTest extends AbstractTestInfra {

	JGitFileSystemsCache cache;
	private JGitFileSystemProviderConfiguration config;

	@Before
	public void setup() {
		config = mock(JGitFileSystemProviderConfiguration.class);
	}

	@Test
	public void addAndGetTest() {
		when(config.getJgitFileSystemsInstancesCache()).thenReturn(2);
		cache = new JGitFileSystemsCache(config);

		assertTrue(cache.fileSystemsSuppliers.isEmpty());
		assertTrue(cache.memoizedSuppliers.isEmpty());

		assertEquals(null

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs1Supplier = () -> fs1;
		cache.addSupplier("fs1"

		assertFalse(cache.fileSystemsSuppliers.isEmpty());
		assertFalse(cache.memoizedSuppliers.isEmpty());

		JGitFileSystemProxy fs1Proxy = (JGitFileSystemProxy) cache.get("fs1");

		assertEquals(fs1

		assertTrue(cache.containsKey("fs1"));

		cache.clear();

		assertTrue(cache.fileSystemsSuppliers.isEmpty());
		assertTrue(cache.memoizedSuppliers.isEmpty());
	}

	@Test
	public void addMoreFSThanCacheSupports() {
		when(config.getJgitFileSystemsInstancesCache()).thenReturn(2);
		cache = new JGitFileSystemsCache(config);

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs1Supplier = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		assertEquals(1
		assertEquals(1

		((JGitFileSystemProxy) cache.get("fs1")).getRealJGitFileSystem();

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
		cache.addSupplier("fs2"
		((JGitFileSystemProxy) cache.get("fs2")).getRealJGitFileSystem();

		assertEquals(2
		assertEquals(2

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
		cache.addSupplier("fs3"

		((JGitFileSystemProxy) cache.get("fs3")).getRealJGitFileSystem();

		assertEquals(3
		assertEquals(2

		((JGitFileSystemProxy) cache.get("fs2")).getRealJGitFileSystem();

		verify(fs2Supplier

		((JGitFileSystemProxy) cache.get("fs3")).getRealJGitFileSystem();

		verify(fs3Supplier

		((JGitFileSystemProxy) cache.get("fs1")).getRealJGitFileSystem();

		verify(fs1Supplier
	}

	@Test
	public void cacheIsOrderedByTheAccessOrder() {

		JGitFileSystemProviderConfiguration config = setupConfigMock();

		setupCacheToTestOrder(config
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));

		setupCacheToTestOrder(config
		cache.get("fs1");
		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));

		setupCacheToTestOrder(config
		cache.get("fs1");
		cache.get("fs2");
		assertFalse(cache.memoizedSuppliers.containsKey("fs3"));

		cache.get("fs1");
		cache.get("fs2");
		cache.get("fs3");
		setupCacheToTestOrder(config
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
	}

	@Test
	public void removeEldestEntryTest() {

		JGitFileSystemProviderConfiguration config = setupConfigMock();

		setupCacheToTestOrder(config
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));

		cache = new JGitFileSystemsCache(config);

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		when(fs1.hasBeenInUse()).thenReturn(true);
		Supplier<JGitFileSystem> fsSupplier1 = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fsSupplier2 = getSupplierSpy(fs2);
		when(fs2.hasBeenInUse()).thenReturn(false);
		cache.addSupplier("fs2"

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fsSupplier = getSupplierSpy(fs3);
		when(fs3.hasBeenInUse()).thenReturn(false);
		cache.addSupplier("fs3"

		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
	}

	@Test
	public void removeEldestEntryTestAllOpen() {

		JGitFileSystemProviderConfiguration config = setupConfigMock();

		cache = new JGitFileSystemsCache(config);

		JGitFileSystem fs1 = mock(JGitFileSystem.class);
		when(fs1.hasBeenInUse()).thenReturn(true);
		Supplier<JGitFileSystem> fsSupplier1 = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
		when(fs2.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs2"

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
		when(fs3.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs3"

		JGitFileSystem fs4 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs4Supplier = getSupplierSpy(fs4);
		when(fs4.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs4"

		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs2"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs4"));

		when(fs1.hasBeenInUse()).thenReturn(false);
		when(fs2.hasBeenInUse()).thenReturn(false);
		when(fs4.hasBeenInUse()).thenReturn(false);

		JGitFileSystem fs5 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs5Supplier = getSupplierSpy(fs5);
		when(fs5.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs5"

		assertTrue(cache.memoizedSuppliers.containsKey("fs3"));
		assertTrue(cache.memoizedSuppliers.containsKey("fs5"));
		assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
		assertFalse(cache.memoizedSuppliers.containsKey("fs2"));
	}

	@Test
	public void fsInUseAreAlwaysOnTheCache() throws IOException

		JGitFileSystemProviderConfiguration config = new JGitFileSystemProviderConfiguration() {
			@Override
			public int getJgitFileSystemsInstancesCache() {
				return 2;
			}
		};

		cache = new JGitFileSystemsCache(config);

		final JGitFileSystemProvider fsProvider = mock(JGitFileSystemProvider.class);

		final Git git = setupGit();

		final JGitFileSystemImpl fs1 = new JGitFileSystemImpl(fsProvider
				new JGitFileSystemLock(git
				CredentialsProvider.getDefault()

		Supplier<JGitFileSystem> fs1Supplier = getSupplierSpy(fs1);
		cache.addSupplier("fs1"

		fs1.lock();
		fs1.lock();
		fs1.unlock();
		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));

		JGitFileSystem fs2 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs2Supplier = getSupplierSpy(fs2);
		when(fs2.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs2"

		JGitFileSystem fs3 = mock(JGitFileSystem.class);
		Supplier<JGitFileSystem> fs3Supplier = getSupplierSpy(fs3);
		when(fs3.hasBeenInUse()).thenReturn(true);
		cache.addSupplier("fs5"

		assertTrue(cache.memoizedSuppliers.containsKey("fs1"));
	}

	private void setupCacheToTestOrder(JGitFileSystemProviderConfiguration config
		cache = new JGitFileSystemsCache(config);

		Arrays.stream(fsNames).forEach(fsName -> {
			JGitFileSystem fs = mock(JGitFileSystem.class);
			Supplier<JGitFileSystem> fsSupplier = getSupplierSpy(fs);
			cache.addSupplier(fsName
		});
	}

	private Supplier<JGitFileSystem> getSupplierSpy(final JGitFileSystem fs1) {
		return spy(new Supplier<JGitFileSystem>() {
			@Override
			public JGitFileSystem get() {
				return fs1;
			}
		});
	}

	private JGitFileSystemProviderConfiguration setupConfigMock() {
		return new JGitFileSystemProviderConfiguration() {
			@Override
			public int getJgitFileSystemsInstancesCache() {
				return 2;
			}

			@Override
			public int getJgitRemoveEldestEntryIterations() {
				return 10;
			}

			@Override
			public int getJgitCacheOverflowCleanupSize() {
				return 10;
			}
		};
	}
}

package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.FileSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsCache;
import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsManager;
import org.eclipse.jgit.niofs.internal.op.commands.Commit;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.JGIT_CACHE_EVICT_THRESHOLD_DURATION;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.JGIT_CACHE_INSTANCES;

public class JGitCacheInvalidationTest extends AbstractTestInfra {

	private JGitFileSystemsCache fsCache;
	private JGitFileSystemsManager fsManager;

	@Before
	public void createGitFsProvider() throws IOException {
		Map<String
		gitPreferences.put(JGIT_CACHE_EVICT_THRESHOLD_DURATION
		gitPreferences.put(JGIT_CACHE_EVICT_THRESHOLD_TIME_UNIT
		gitPreferences.put(JGIT_CACHE_INSTANCES
		provider = new JGitFileSystemProvider(gitPreferences);
		fsManager = provider.getFsManager();
		fsCache = fsManager.getFsCache();
	}

	@Test
	public void testTwoInstancesForSameFS() throws IOException {
		String fs1Name = "dora";
		String fs2Name = "bento";
		String fs3Name = "bela";

				EMPTY_ENV);
		final JGitFileSystemImpl realInstanceFs1 = (JGitFileSystemImpl) fs1.getRealJGitFileSystem();


		assertThat(fs1).isNotNull();
		assertThat(fs2).isNotNull();
		assertThat(fs3).isNotNull();

		assertThat(fs1).isInstanceOf(JGitFileSystemProxy.class);
		assertThat(fs2).isInstanceOf(JGitFileSystemProxy.class);
		assertThat(fs3).isInstanceOf(JGitFileSystemProxy.class);

		assertThat(fsCache.getFileSystems()).contains(fs1.getName());
		assertThat(fsCache.getFileSystems()).contains(((JGitFileSystem) fs2).getName());
		assertThat(fsCache.getFileSystems()).contains(((JGitFileSystem) fs3).getName());

		JGitFileSystemsCache.JGitFileSystemsCacheInfo cacheInfo = fsCache.getCacheInfo();

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(((JGitFileSystem) fs2).getName());
		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(((JGitFileSystem) fs3).getName());

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).doesNotContain(fs1.getName());

		JGitFileSystemProxy anotherInstanceOfFs1Proxy = (JGitFileSystemProxy) fsManager.get(fs1Name);
		JGitFileSystemImpl anotherInstanceOfFs1 = (JGitFileSystemImpl) anotherInstanceOfFs1Proxy
				.getRealJGitFileSystem();

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(fs1.getName());
		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).contains(((JGitFileSystem) fs3).getName());

		assertThat(cacheInfo.memoizedFileSystemsCacheKeys()).doesNotContain(((JGitFileSystem) fs2).getName());

		assertThat(realInstanceFs1.getName()).isEqualToIgnoringCase(anotherInstanceOfFs1.getName());
		assertThat(realInstanceFs1.getLock()).isEqualTo(anotherInstanceOfFs1.getLock());

		new Commit(realInstanceFs1.getGit()
				new HashMap<String
					{
						put("realInstanceFs1File.txt"
					}
				}).execute();

		InputStream stream = provider.newInputStream(anotherInstanceOfFs1.getPath("realInstanceFs1File.txt"));
		assertNotNull(stream);
		String content = new Scanner(stream).useDelimiter("\\A").next();
		assertEquals("dora"

		new Commit(anotherInstanceOfFs1.getGit()
				new HashMap<String
					{
						put("anotherInstanceOfFs1File.txt"
					}
				}).execute();

		stream = provider.newInputStream(realInstanceFs1.getPath("anotherInstanceOfFs1File.txt"));
		assertNotNull(stream);
		content = new Scanner(stream).useDelimiter("\\A").next();
		assertEquals("bento"

		realInstanceFs1.lock();
		assertThat(realInstanceFs1.hasBeenInUse()).isTrue();
		assertThat(anotherInstanceOfFs1.hasBeenInUse()).isTrue();

		realInstanceFs1.unlock();
	}
}

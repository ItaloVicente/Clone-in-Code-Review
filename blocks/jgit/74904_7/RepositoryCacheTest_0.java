
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.RepositoryCacheConfig.AUTO_CLEANUP_DELAY;
import static org.eclipse.jgit.lib.RepositoryCacheConfig.NO_CLEANUP;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.junit.Before;
import org.junit.Test;

public class RepositoryCacheConfigTest {

	private RepositoryCacheConfig config;

	@Before
	public void setUp() {
		config = new RepositoryCacheConfig();
	}

	@Test
	public void testDefaultValues() {
		assertEquals(TimeUnit.HOURS.toMillis(1)
		assertEquals(config.getExpireAfter() / 10
	}

	@Test
	public void testCleanupDelay() {
		config.setCleanupDelay(TimeUnit.HOURS.toMillis(1));
		assertEquals(TimeUnit.HOURS.toMillis(1)
	}

	@Test
	public void testAutoCleanupDelay() {
		config.setExpireAfter(TimeUnit.MINUTES.toMillis(20));
		config.setCleanupDelay(AUTO_CLEANUP_DELAY);
		assertEquals(TimeUnit.MINUTES.toMillis(20)
		assertEquals(config.getExpireAfter() / 10
	}

	@Test
	public void testAutoCleanupDelayShouldBeMax10minutes() {
		config.setExpireAfter(TimeUnit.HOURS.toMillis(10));
		assertEquals(TimeUnit.HOURS.toMillis(10)
		assertEquals(TimeUnit.MINUTES.toMillis(10)
	}

	@Test
	public void testDisabledCleanupDelay() {
		config.setCleanupDelay(NO_CLEANUP);
		assertEquals(NO_CLEANUP
	}

	@Test
	public void testFromConfig() throws ConfigInvalidException {
		Config otherConfig = new Config();
		otherConfig.fromText("[core]\nrepositoryCacheExpireAfter=1000\n"
				+ "repositoryCacheCleanupDelay=500");
		config.fromConfig(otherConfig);
		assertEquals(1000
		assertEquals(500
	}
}

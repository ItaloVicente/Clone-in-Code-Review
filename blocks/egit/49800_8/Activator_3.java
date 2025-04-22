package org.eclipse.egit.core.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.jgit.merge.MergeStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PreferredMergeStrategyTest {

	private Activator a;

	@Before
	public void setUp() {
		a = Activator.getDefault();
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_preferredMergeStrategy);
	}

	@After
	public void tearDown() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_preferredMergeStrategy);
	}

	@Test
	public void testGetDefaultPreferredMergeStrategy() {
		assertNull(a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenNoPref() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_preferredMergeStrategy);

		assertNull(a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenInvalidPreference() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.put(GitCorePreferences.core_preferredMergeStrategy,
						"invalid value");

		assertNull(a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenValidPreference() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).put(
				GitCorePreferences.core_preferredMergeStrategy, "resolve");

		assertSame(MergeStrategy.RESOLVE, a.getPreferredMergeStrategy());
	}
}

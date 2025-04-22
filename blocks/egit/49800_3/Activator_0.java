package org.eclipse.egit.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferenceInitializer;
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
				GitCorePreferences.core_preferredModelMergeStrategy);
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_enableLogicalModel);
	}

	@After
	public void tearDown() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_preferredModelMergeStrategy);
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_enableLogicalModel);
	}

	@Test
	public void testInitializeDefaultPreferences() {
		GitCorePreferenceInitializer initializer = new GitCorePreferenceInitializer();
		initializer.initializeDefaultPreferences();
		final IEclipsePreferences p = DefaultScope.INSTANCE.getNode(Activator
				.getPluginId());
		assertEquals("recursive", p.get(
				GitCorePreferences.core_preferredModelMergeStrategy, "error"));
		assertTrue(p.getBoolean(GitCorePreferences.core_enableLogicalModel,
				false));
	}

	@Test
	public void testGetDefaultPreferredMergeStrategy() {
		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenModelDisabled() {
		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).put(
				GitCorePreferences.core_preferredModelMergeStrategy, "resolve");
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).putBoolean(
				GitCorePreferences.core_enableLogicalModel, false);

		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenModelEnabledButNoPref() {
		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).remove(
				GitCorePreferences.core_preferredModelMergeStrategy);
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).putBoolean(
				GitCorePreferences.core_enableLogicalModel, true);

		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenModelEnabledAndInvalidPreference() {
		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).put(
				GitCorePreferences.core_preferredModelMergeStrategy,
				"invalid value");
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).putBoolean(
				GitCorePreferences.core_enableLogicalModel, true);

		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
	}

	@Test
	public void testGetPreferredMergeStrategyWhenModelDisabledAndValidPreference() {
		assertSame(MergeStrategy.RECURSIVE, a.getPreferredMergeStrategy());
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).put(
				GitCorePreferences.core_preferredModelMergeStrategy, "resolve");
		InstanceScope.INSTANCE.getNode(Activator.getPluginId()).putBoolean(
				GitCorePreferences.core_enableLogicalModel, true);

		assertSame(MergeStrategy.RESOLVE, a.getPreferredMergeStrategy());
	}
}

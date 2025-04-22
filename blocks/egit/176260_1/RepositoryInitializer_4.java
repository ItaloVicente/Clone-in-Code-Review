package org.eclipse.egit.core;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffCache;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.util.SystemReader;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component
public class RepositoryInitializer {

	private IWorkspace workspace;

	private IPreferencesService preferencesService;

	private ServiceRegistration<RepositoryCache> registration;

	@Reference
	void setWorkspace(IWorkspace workspace) {
		this.workspace = workspace;
	}

	@Reference
	void setPreferencesService(IPreferencesService service) {
		this.preferencesService = service;
	}

	@Activate
	void start() {
		try {
			reconfigureWindowCache(preferencesService);
		} catch (RuntimeException | ExceptionInInitializerError e) {
			Activator.logError(CoreText.Activator_ReconfigureWindowCacheError,
					e);
		}
		RepositoryCache cache = RepositoryCache.getInstance();
		IndexDiffCache.getInstance();
		RepositoryUtil.create(workspace.getRoot().getLocation());
		registration = FrameworkUtil.getBundle(getClass()).getBundleContext()
				.registerService(RepositoryCache.class, cache, null);
	}

	@Deactivate
	void shutDown() {
		registration.unregister();
		RepositoryUtil.getInstance().dispose();
		IndexDiffCache.getInstance().dispose();
		RepositoryCache.getInstance().clear();
	}

	public static void reconfigureWindowCache() {
		reconfigureWindowCache(Platform.getPreferencesService());
	}

	private static void reconfigureWindowCache(IPreferencesService prefs) {
		WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitLimit(prefs.getInt(Activator.PLUGIN_ID,
				GitCorePreferences.core_packedGitLimit, 0, null));
		c.setPackedGitWindowSize(prefs.getInt(Activator.PLUGIN_ID,
				GitCorePreferences.core_packedGitWindowSize, 0, null));
		if (SystemReader.getInstance().isWindows()) {
			c.setPackedGitMMAP(false);
		} else {
			c.setPackedGitMMAP(prefs.getBoolean(Activator.PLUGIN_ID,
					GitCorePreferences.core_packedGitMMAP, false, null));
		}
		c.setDeltaBaseCacheLimit(prefs.getInt(Activator.PLUGIN_ID,
				GitCorePreferences.core_deltaBaseCacheLimit, 0, null));
		c.setStreamFileThreshold(prefs.getInt(Activator.PLUGIN_ID,
				GitCorePreferences.core_streamFileThreshold, 0, null));
		c.setExposeStatsViaJmx(false);
		c.install();
	}
}

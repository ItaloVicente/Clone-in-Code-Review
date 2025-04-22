package org.eclipse.egit.core.internal;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.GitCorePreferences;
import org.eclipse.egit.core.MergeStrategyDescriptor;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.util.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

public class MergeStrategies {

	public static final String EXTENSION_POINT = "org.eclipse.egit.core.mergeStrategy"; //$NON-NLS-1$

	private static volatile Loader loader;

	private MergeStrategies() {
	}


	public static MergeStrategy getPreferredMergeStrategy() {
		String key = Platform.getPreferencesService().getString(
				Activator.PLUGIN_ID,
				GitCorePreferences.core_preferredMergeStrategy, null, null);
		if (!StringUtils.isEmptyOrNull(key)
				&& !GitCorePreferences.core_preferredMergeStrategy_Default
						.equals(key)) {
			Loader loadJob = loader;
			boolean jobDone = loadJob != null && loadJob.isDone();
			MergeStrategy result = MergeStrategy.get(key);
			if (result != null) {
				return result;
			}
			if (jobDone) {
				Activator.logError(MessageFormat.format(
						CoreText.Activator_invalidPreferredMergeStrategy, key),
						null);
			}
		}
		return null;
	}

	@NonNull
	public static Collection<MergeStrategyDescriptor> getRegisteredMergeStrategies() {
		Loader loadJob = loader;
		if (loadJob != null && loadJob.isDone()) {
			return loadJob.getRegisteredMergeStrategies();
		}
		return Collections.emptyList();
	}

	@Component
	public static class Loader extends Job {

		private IExtensionRegistry registry;

		private volatile MergeStrategyRegistryListener mergeStrategyRegistryListener;

		private volatile boolean done;

		public Loader() {
			super(CoreText.MergeStrategy_LoaderJob);
			setSystem(true);
			setUser(false);
		}

		@Reference
		void setExtensionRegistry(IExtensionRegistry registry) {
			this.registry = registry;
		}

		@Reference
		void setWorkspace(@SuppressWarnings("unused") IWorkspace workspace) {
		}

		boolean isDone() {
			return done;
		}

		Collection<MergeStrategyDescriptor> getRegisteredMergeStrategies() {
			MergeStrategyRegistryListener listener = mergeStrategyRegistryListener;
			if (listener != null) {
				return listener.getCurrentStrategies();
			}
			return Collections.emptyList();
		}

		@Activate
		void start() {
			schedule();
			loader = this;
		}

		@Deactivate
		void shutDown() {
			loader = null;
			cancel();
			try {
				join();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			if (mergeStrategyRegistryListener != null) {
				registry.removeListener(mergeStrategyRegistryListener);
				mergeStrategyRegistryListener = null;
			}
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			try {
				MergeStrategyRegistryListener listener = new MergeStrategyRegistryListener(
						registry);
				if (!monitor.isCanceled()) {
					registry.addListener(listener,
							MergeStrategies.EXTENSION_POINT);
					mergeStrategyRegistryListener = listener;
					return Status.OK_STATUS;
				}
			} finally {
				done = true;
				monitor.done();
			}
			return Status.CANCEL_STATUS;
		}
	}

	private static class MergeStrategyRegistryListener
			implements IRegistryEventListener {

		private Map<String, MergeStrategyDescriptor> strategies = new LinkedHashMap<>();

		private volatile Collection<MergeStrategyDescriptor> currentStrategies = Collections
				.emptyList();

		private MergeStrategyRegistryListener(IExtensionRegistry registry) {
			IConfigurationElement[] elements = registry
					.getConfigurationElementsFor(
							MergeStrategies.EXTENSION_POINT);
			if (loadMergeStrategies(elements)) {
				currentStrategies = getStrategies();
			}
		}

		private Collection<MergeStrategyDescriptor> getStrategies() {
			return new ArrayList<>(strategies.values());
		}

		@NonNull
		public Collection<MergeStrategyDescriptor> getCurrentStrategies() {
			return Collections.unmodifiableCollection(currentStrategies);
		}

		@Override
		public void added(IExtension[] extensions) {
			boolean changed = false;
			for (IExtension extension : extensions) {
				changed |= loadMergeStrategies(
						extension.getConfigurationElements());
			}
			if (changed) {
				currentStrategies = getStrategies();
			}
		}

		@Override
		public void added(IExtensionPoint[] extensionPoints) {
		}

		@Override
		public void removed(IExtension[] extensions) {
			boolean changed = false;
			for (IExtension extension : extensions) {
				for (IConfigurationElement element : extension
						.getConfigurationElements()) {
					try {
						Object ext = element.createExecutableExtension("class"); //$NON-NLS-1$
						if (ext instanceof MergeStrategy) {
							MergeStrategy strategy = (MergeStrategy) ext;
							changed |= strategies
									.remove(strategy.getName()) != null;
						}
					} catch (CoreException e) {
						Activator.logError(CoreText.MergeStrategy_UnloadError,
								e);
					}
				}
			}
			if (changed) {
				currentStrategies = getStrategies();
			}
		}

		@Override
		public void removed(IExtensionPoint[] extensionPoints) {
		}

		private boolean loadMergeStrategies(IConfigurationElement[] elements) {
			boolean changed = false;
			for (IConfigurationElement element : elements) {
				try {
					Object ext = element.createExecutableExtension("class"); //$NON-NLS-1$
					if (ext instanceof MergeStrategy) {
						MergeStrategy strategy = (MergeStrategy) ext;
						String name = element.getAttribute("name"); //$NON-NLS-1$
						if (name == null || name.isEmpty()) {
							name = strategy.getName();
						}
						if (canRegister(name, strategy)) {
							if (MergeStrategy.get(name) == null) {
								MergeStrategy.register(name, strategy);
							}
							strategies.put(name,
									new MergeStrategyDescriptor(name,
											element.getAttribute("label"), //$NON-NLS-1$
											strategy.getClass()));
							changed = true;
						}
					}
				} catch (CoreException e) {
					Activator.logError(CoreText.MergeStrategy_LoadError, e);
				}
			}
			return changed;
		}

		private boolean canRegister(String name, MergeStrategy strategy) {
			boolean result = true;
			if (name == null || name.isEmpty()) {
				Activator.logError(
						MessageFormat.format(CoreText.MergeStrategy_MissingName,
								strategy.getClass()),
						null);
				result = false;
			} else if (strategies.containsKey(name)) {
				Activator.logError(MessageFormat.format(
						CoreText.MergeStrategy_DuplicateName, name,
						strategies.get(name).getImplementedBy(),
						strategy.getClass()), null);
				result = false;
			} else if (MergeStrategy.get(name) != null
					&& MergeStrategy.get(name) != strategy) {
				Activator.logError(MessageFormat.format(
						CoreText.MergeStrategy_ReservedName, name,
						MergeStrategy.get(name).getClass(),
						strategy.getClass()), null);
				result = false;
			}
			return result;
		}
	}
}

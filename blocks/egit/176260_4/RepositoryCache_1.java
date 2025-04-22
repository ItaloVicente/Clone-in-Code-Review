package org.eclipse.egit.core;

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
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.egit.core.internal.CoreText;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.osgi.util.NLS;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

@Component
public class MergeStrategyTracker extends Job {


	private static final String EXTENSION_POINT = "org.eclipse.egit.core.mergeStrategy"; //$NON-NLS-1$

	private IExtensionRegistry registry;

	private volatile MergeStrategyRegistryListener mergeStrategyRegistryListener;

	public MergeStrategyTracker() {
		super("Merge strategy initializer"); //$NON-NLS-1$
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

	@Activate
	void start() {
		schedule();
	}

	@Deactivate
	void shutDown() {
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
				registry.addListener(listener, EXTENSION_POINT);
				mergeStrategyRegistryListener = listener;
				return Status.OK_STATUS;
			}
		} finally {
			monitor.done();
		}
		return Status.CANCEL_STATUS;
	}

	private static class MergeStrategyRegistryListener
			implements IRegistryEventListener {

		private Map<String, MergeStrategyDescriptor> strategies = new LinkedHashMap<>();

		private MergeStrategyRegistryListener(IExtensionRegistry registry) {
			IConfigurationElement[] elements = registry
					.getConfigurationElementsFor(EXTENSION_POINT);
			if (loadMergeStrategies(elements)) {
				Activator.getDefault().setMergeStrategies(getStrategies());
			}
		}

		private Collection<MergeStrategyDescriptor> getStrategies() {
			return Collections.unmodifiableCollection(strategies.values());
		}

		@Override
		public void added(IExtension[] extensions) {
			boolean changed = false;
			for (IExtension extension : extensions) {
				changed |= loadMergeStrategies(
						extension.getConfigurationElements());
			}
			if (changed) {
				Activator.getDefault().setMergeStrategies(getStrategies());
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
				Activator.getDefault().setMergeStrategies(getStrategies());
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
				Activator.logError(NLS.bind(CoreText.MergeStrategy_MissingName,
						strategy.getClass()), null);
				result = false;
			} else if (strategies.containsKey(name)) {
				Activator
						.logError(NLS.bind(CoreText.MergeStrategy_DuplicateName,
								new Object[] { name,
										strategies.get(name).getImplementedBy(),
										strategy.getClass() }),
								null);
				result = false;
			} else if (MergeStrategy.get(name) != null
					&& MergeStrategy.get(name) != strategy) {
				Activator.logError(NLS.bind(CoreText.MergeStrategy_ReservedName,
						new Object[] { name, MergeStrategy.get(name).getClass(),
								strategy.getClass() }),
						null);
				result = false;
			}
			return result;
		}
	}
}

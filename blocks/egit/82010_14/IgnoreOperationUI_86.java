package org.eclipse.egit.ui.internal.operations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.egit.core.synchronize.GitResourceVariantTreeSubscriber;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.team.core.subscribers.SubscriberScopeManager;
import org.eclipse.ui.IContributorResourceAdapter;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.ide.IContributorResourceAdapter2;
import org.eclipse.ui.progress.IProgressService;

public class GitScopeUtil {

	public static IResource[] getRelatedChanges(IWorkbenchPart part,
			IResource[] resources) throws ExecutionException,
			InterruptedException {
		if (part == null)
			throw new IllegalArgumentException();
		if (resources == null)
			return new IResource[0];
		IResource[] resourcesInScope;
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {

			try {
				resourcesInScope = findRelatedChanges(part, resources);
			} catch (InvocationTargetException e) {
				Activator.handleError(
						UIText.CommitActionHandler_errorBuildingScope,
						e.getCause(), true);
				resourcesInScope = resources;
			}
		} else {
			resourcesInScope = resources;
		}
		return resourcesInScope;
	}

	private static SubscriberScopeManager createScopeManager(
			final IResource[] resources, IProgressMonitor monitor) {
		ResourceMapping[] mappings = GitScopeUtil
				.getResourceMappings(resources);
		GitSynchronizeDataSet set = new GitSynchronizeDataSet();
		final GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
				set);
		monitor.setTaskName(UIText.GitModelSynchronize_fetchGitDataJobName);
		subscriber.init(monitor);
		SubscriberScopeManager manager = new SubscriberScopeManager(
				UIText.GitScopeOperation_GitScopeManager, mappings, subscriber,
				true);
		return manager;
	}

	@Nullable
	private static ResourceMapping getResourceMapping(Object o) {
		ResourceMapping mapping = AdapterUtils.adapt(o, ResourceMapping.class);
		if (mapping != null) {
			return mapping;
		}
		if (o instanceof IAdaptable) {
			IContributorResourceAdapter adapted = AdapterUtils.adapt(o,
					IContributorResourceAdapter.class);
			if (adapted instanceof IContributorResourceAdapter2) {
				IContributorResourceAdapter2 cra = (IContributorResourceAdapter2) adapted;
				return cra.getAdaptedResourceMapping((IAdaptable) o);
			}
		}
		return null;
	}

	private static ResourceMapping[] getResourceMappings(IResource[] resources) {
		List<ResourceMapping> result = new ArrayList<>();
		for (IResource resource : resources)
			result.add(getResourceMapping(resource));
		return result.toArray(new ResourceMapping[result.size()]);
	}

	private static IResource[] findRelatedChanges(final IWorkbenchPart part,
			final IResource[] selectedResources)
			throws InvocationTargetException, InterruptedException {

		final List<IResource> relatedChanges = new ArrayList<>();
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				try {
					monitor.beginTask(
							UIText.CommitActionHandler_lookingForChanges, 100);
					List<IResource> collectedResources = collectRelatedChanges(
							selectedResources, part, monitor);
					relatedChanges.addAll(collectedResources);
				} finally {
					monitor.done();
				}
			}

		};

		IProgressService progressService = CommonUtils.getService(part.getSite(), IProgressService.class);
		progressService.run(true, true, runnable);

		return relatedChanges.toArray(new IResource[relatedChanges.size()]);
	}

	private static List<IResource> collectRelatedChanges(
			IResource[] selectedResources, IWorkbenchPart part,
			IProgressMonitor monitor) throws InterruptedException,
			InvocationTargetException {

		SubscriberScopeManager manager = GitScopeUtil.createScopeManager(
				selectedResources, new SubProgressMonitor(monitor, 50));
		GitScopeOperation buildScopeOperation = GitScopeOperationFactory
				.getFactory().createGitScopeOperation(part, manager);

		buildScopeOperation.run(new SubProgressMonitor(monitor, 50));

		return buildScopeOperation.getRelevantResources();
	}

}

package org.eclipse.egit.ui.internal.synchronize;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.synchronize.GitLazyResourceVariantTreeSubscriber;
import org.eclipse.egit.core.synchronize.GitSubscriberResourceMappingContext;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.dialogs.CompareTreeView;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class ModelAwareGitSynchronizer extends DefaultGitSynchronizer {

	private ResourceMappingContext context;

	@Override
	public void compare(IResource[] resources, Repository repository,
			String leftRev, String rightRev, boolean includeLocal,
			IWorkbenchPage page) throws IOException {
		try {
			PlatformUI.getWorkbench().getProgressService()
					.busyCursorWhile(new IRunnableWithProgress() {

						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								Set<IResource> resourceSet = new LinkedHashSet<>(
										Arrays.asList(resources));
								context = createResourceMappingContext(
										resourceSet, repository, leftRev,
										rightRev, includeLocal, monitor);
								ModelAwareGitSynchronizer.super.compare(
										resources, repository, leftRev,
										rightRev, includeLocal, page);
							} catch (IOException e) {
								throw new InvocationTargetException(e);
							}
						}
					});
		} catch (InvocationTargetException e) {
			Activator.error(e.getTargetException().getMessage(),
					e.getTargetException());
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void compare(IFile file, Repository repository, String leftPath,
			String rightPath, String leftRev, String rightRev,
			boolean includeLocal, IWorkbenchPage page) throws IOException {
		try {
			PlatformUI.getWorkbench().getProgressService()
					.busyCursorWhile(new IRunnableWithProgress() {

						@Override
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								context = createResourceMappingContext(
										Collections.singleton(file), repository,
										leftRev, rightRev, includeLocal,
										monitor);
								ModelAwareGitSynchronizer.super.compare(file,
										repository, leftPath, rightPath,
										leftRev, rightRev, includeLocal, page);
							} catch (IOException e) {
								throw new InvocationTargetException(e);
							}
						}
					});
		} catch (InvocationTargetException e) {
			Activator.error(e.getTargetException().getMessage(),
					e.getTargetException());
		} catch (InterruptedException e) {
		}
	}

	@Override
	protected boolean canCompareDirectly(IFile file) {
		final ResourceMapping[] mappings = ResourceUtil
				.getResourceMappings(file, context);

		for (ResourceMapping mapping : mappings) {
			try {
				final ResourceTraversal[] traversals = mapping
						.getTraversals(context, null);
				for (ResourceTraversal traversal : traversals) {
					final IResource[] traversalResources = traversal
							.getResources();
					if (traversalResources.length > 1 && Arrays
							.asList(traversalResources).contains(file)) {
						return false;
					}
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return true;
	}

	@Override
	protected void synchronize(IResource[] resources, Repository repository,
			String leftRev, String rightRev, boolean includeLocal)
			throws IOException {
		if (rightRev.equals(GitFileRevision.INDEX)) {
			openGitTreeCompare(resources, leftRev,
					CompareTreeView.INDEX_VERSION, includeLocal);
		} else {
			final Set<IResource> includedResources = new HashSet<>(
					Arrays.asList(resources));
			final Set<ResourceMapping> allMappings = new HashSet<>();

			Set<IResource> newResources = new HashSet<>(includedResources);
			do {
				final Set<IResource> copy = newResources;
				newResources = new HashSet<>();
				for (IResource resource : copy) {
					Assert.isNotNull(resource);
					ResourceMapping[] mappings = ResourceUtil
							.getResourceMappings(resource, context);
					allMappings.addAll(Arrays.asList(mappings));
					newResources.addAll(collectResources(mappings));
				}
			} while (includedResources.addAll(newResources));

			if (GitFileRevision.INDEX.equals(leftRev)) {
				final ResourceMapping[] mappings = allMappings
						.toArray(new ResourceMapping[allMappings.size()]);
				final GitSynchronizeData data = new GitSynchronizeData(
						repository, leftRev, rightRev, true, includedResources);
				GitModelSynchronize.launch(new GitSynchronizeDataSet(data),
						mappings);
			} else {
				final ResourceMapping[] mappings = allMappings
						.toArray(new ResourceMapping[allMappings.size()]);
				final GitSynchronizeData data = new GitSynchronizeData(
						repository, leftRev, rightRev, includeLocal,
						includedResources);
				GitModelSynchronize.launch(new GitSynchronizeDataSet(data),
						mappings);
			}
		}
	}

	protected ResourceMappingContext createResourceMappingContext(
			Set<IResource> resources, Repository repository, String leftRev,
			String rightRev, boolean includeLocal, IProgressMonitor monitor) {
		try {
			GitSynchronizeData gsd = new GitSynchronizeData(repository, leftRev,
					rightRev, includeLocal, resources);
			GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
			GitLazyResourceVariantTreeSubscriber subscriber = new GitLazyResourceVariantTreeSubscriber(
					gsds);
			subscriber.init(monitor);

			return new GitSubscriberResourceMappingContext(subscriber, gsds);
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}
		return ResourceMappingContext.LOCAL_CONTEXT;
	}

	private Set<IResource> collectResources(ResourceMapping[] mappings) {
		final Set<IResource> result = new HashSet<>();
		for (ResourceMapping mapping : mappings) {
			try {
				ResourceTraversal[] traversals = mapping.getTraversals(context,
						new NullProgressMonitor());
				for (ResourceTraversal traversal : traversals)
					result.addAll(Arrays.asList(traversal.getResources()));
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return result;
	}
}

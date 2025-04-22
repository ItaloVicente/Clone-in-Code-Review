package org.eclipse.egit.ui.internal.synchronize;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.RemoteResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.core.internal.util.ResourceUtil;
import org.eclipse.egit.core.synchronize.GitResourceVariantTreeSubscriber;
import org.eclipse.egit.core.synchronize.GitSubscriberResourceMappingContext;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.dialogs.CompareTreeView;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Repository;

public class ModelAwareSyncStrategy extends SyncStrategy {

	private ResourceMappingContext context;

	public ModelAwareSyncStrategy(IResource[] resources,
			@NonNull Repository repository, String leftRev, String rightRev,
			boolean includeLocal) {
		super(resources, repository, leftRev, rightRev, includeLocal);
	}

	@Override
	public void synchronize() throws IOException {
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
						.getResourceMappings(resource,
								getResourceMappingContext());
				allMappings.addAll(Arrays.asList(mappings));
				newResources.addAll(collectResources(mappings));
			}
		} while (includedResources.addAll(newResources));

		if (rightRev.equals(GitFileRevision.INDEX)) {
			final IResource[] resourcesArray = includedResources
					.toArray(new IResource[includedResources.size()]);
			GitModelSynchronize.openGitTreeCompare(resourcesArray, leftRev,
					CompareTreeView.INDEX_VERSION, includeLocal);
		} else if (leftRev.equals(GitFileRevision.INDEX)) {
			final ResourceMapping[] mappings = allMappings
					.toArray(new ResourceMapping[allMappings.size()]);
			final GitSynchronizeData data = new GitSynchronizeData(repository,
					leftRev, rightRev, true, includedResources);
			GitModelSynchronize.launch(new GitSynchronizeDataSet(data),
					mappings);
		} else {
			final ResourceMapping[] mappings = allMappings
					.toArray(new ResourceMapping[allMappings.size()]);
			final GitSynchronizeData data = new GitSynchronizeData(repository,
					leftRev, rightRev, includeLocal, includedResources);
			GitModelSynchronize.launch(new GitSynchronizeDataSet(data),
					mappings);
		}
	}

	@Override
	protected boolean canCompareDirectly() {
		if (resources.length == 1) {
			IResource resource = resources[0];
			if (resource instanceof IFile) {
				return canCompareDirectly((IFile) resource);
			} else {
				IPath location = resource.getLocation();
				if (location != null
						&& Files.isSymbolicLink(location.toFile().toPath())) {
					return true;
				}
			}
		}
		return false;
	}

	private ResourceMappingContext getResourceMappingContext() {
		if (context == null) {
			try {
				GitSynchronizeData gsd = new GitSynchronizeData(repository,
						leftRev, rightRev, includeLocal);
				GitSynchronizeDataSet gsds = new GitSynchronizeDataSet(gsd);
				GitResourceVariantTreeSubscriber subscriber = new GitResourceVariantTreeSubscriber(
						gsds);
				subscriber.init(new NullProgressMonitor());

				context = new GitSubscriberResourceMappingContext(subscriber,
						gsds);
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
				context = ResourceMappingContext.LOCAL_CONTEXT;
			}
		}
		return context;
	}

	private Set<IResource> collectResources(ResourceMapping[] mappings) {
		final Set<IResource> result = new HashSet<>();
		for (ResourceMapping mapping : mappings) {
			try {
				ResourceTraversal[] traversals = mapping.getTraversals(
						getResourceMappingContext(), new NullProgressMonitor());
				for (ResourceTraversal traversal : traversals)
					result.addAll(Arrays.asList(traversal.getResources()));
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return result;
	}

	private boolean canCompareDirectly(@NonNull IFile file) {
		final ResourceMapping[] mappings = ResourceUtil
				.getResourceMappings(file, getResourceMappingContext());

		for (ResourceMapping mapping : mappings) {
			try {
				final ResourceTraversal[] traversals = mapping
						.getTraversals(getResourceMappingContext(), null);
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
}

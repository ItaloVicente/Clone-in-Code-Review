package org.eclipse.egit.core.internal.merge;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.IModelProviderDescriptor;
import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.resources.mapping.RemoteResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.resources.mapping.ResourceTraversal;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.egit.core.Activator;

public final class LogicalModels {
	private static final Set<String> ignoredModelDescriptors = new HashSet<String>(
			Arrays.asList("org.eclipse.core.resources.modelProvider", //$NON-NLS-1$
					"org.eclipse.jdt.ui.modelProvider", //$NON-NLS-1$
					"org.eclipse.egit.ui.changeSetModel")); //$NON-NLS-1$

	private Map<IResource, Set<IResource>> models = new HashMap<IResource, Set<IResource>>();

	public void build(Set<IResource> resources,
			RemoteResourceMappingContext remoteMappingContext) {
		for (IResource supervisedResource : resources)
			if (supervisedResource.isAccessible()
					&& supervisedResource instanceof IFile
					&& !models.containsKey(supervisedResource)) {
				final Set<IResource> model = discoverModel(supervisedResource,
						remoteMappingContext);
				for (IResource resourceInModel : model)
					models.put(resourceInModel, model);
			}
	}

	public Set<IResource> getModel(IResource resource) {
		return models.get(resource);
	}

	public static Set<IResource> discoverModel(IResource resource,
			ResourceMappingContext mappingContext) {
		final Set<IResource> model = new LinkedHashSet<IResource>();

		Set<IResource> newResources = new LinkedHashSet<IResource>();
		newResources.add(resource);
		do {
			final Set<IResource> temp = newResources;
			newResources = new LinkedHashSet<IResource>();
			for (IResource res : temp) {
				final Set<ResourceMapping> mappings = getResourceMappings(
						Collections.singleton(res), mappingContext);
				newResources.addAll(collectResources(mappings, mappingContext));
			}
		} while (model.addAll(newResources));

		return model;
	}

	@SuppressWarnings("unchecked")
	public static <T> T findAdapter(Set<IResource> model, Class<T> adapterClass)
			throws CoreException {
		final IResource[] modelArray = model
				.toArray(new IResource[model.size()]);
		final IModelProviderDescriptor[] descriptors = ModelProvider
				.getModelProviderDescriptors();

		T adapter = null;
		for (int i = 0; i < descriptors.length && adapter == null; i++) {
			final IModelProviderDescriptor descriptor = descriptors[i];
			if (ignoredModelDescriptors.contains(descriptor.getId()))
				continue;

			final IResource[] matchingResources = descriptor
					.getMatchingResources(modelArray);
			if (matchingResources.length == modelArray.length) {
				final ModelProvider provider = descriptor.getModelProvider();
				adapter = (T) provider.getAdapter(adapterClass);
			} else {
			}
		}

		return adapter;
	}

	public static Set<ResourceMapping> getResourceMappings(
			Set<IResource> model, ResourceMappingContext mappingContext) {
		final Set<ResourceMapping> allMappings = new LinkedHashSet<ResourceMapping>();
		final IResource[] modelArray = model
				.toArray(new IResource[model.size()]);
		final IModelProviderDescriptor[] descriptors = ModelProvider
				.getModelProviderDescriptors();

		for (IModelProviderDescriptor descriptor : descriptors) {
			if (ignoredModelDescriptors.contains(descriptor.getId()))
				continue;

			try {
				final IResource[] matchingResources = descriptor
						.getMatchingResources(modelArray);
				if (matchingResources.length > 0) {
					final ModelProvider modelProvider = descriptor
							.getModelProvider();
					for (IResource resource : model) {
						final ResourceMapping[] modelMappings = modelProvider
								.getMappings(resource, mappingContext,
										new NullProgressMonitor());
						allMappings.addAll(Arrays.asList(modelMappings));
					}
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}

		return allMappings;
	}

	private static Set<IResource> collectResources(
			Set<ResourceMapping> mappings,
			ResourceMappingContext mappingContext) {
		final Set<IResource> resources = new LinkedHashSet<IResource>();
		for (ResourceMapping mapping : mappings) {
			try {
				final ResourceTraversal[] traversals = mapping.getTraversals(
						mappingContext, new NullProgressMonitor());
				for (ResourceTraversal traversal : traversals)
					resources.addAll(Arrays.asList(traversal.getResources()));
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return resources;
	}
}

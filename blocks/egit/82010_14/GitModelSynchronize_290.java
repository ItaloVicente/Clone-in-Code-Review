package org.eclipse.egit.ui.internal.synchronize;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ModelProvider;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.resources.mapping.ResourceMappingContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.egit.core.synchronize.GitSubscriberResourceMappingContext;
import org.eclipse.egit.core.synchronize.IgnoreInGitSynchronizations;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelObject;

public class GitChangeSetModelProvider extends ModelProvider
		implements IgnoreInGitSynchronizations {

	public static final String ID = "org.eclipse.egit.ui.changeSetModel"; //$NON-NLS-1$

	private static GitChangeSetModelProvider provider;

	public static GitChangeSetModelProvider getProvider() {
		if (provider == null) {
			ModelProvider modelProvider;
			try {
				modelProvider = ModelProvider.getModelProviderDescriptor(ID)
						.getModelProvider();
				provider = (GitChangeSetModelProvider) modelProvider;
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}

		return provider;
	}

	@Override
	public ResourceMapping[] getMappings(IResource resource,
			ResourceMappingContext context, IProgressMonitor monitor)
			throws CoreException {
		if (context instanceof GitSubscriberResourceMappingContext) {
			GitSubscriberResourceMappingContext gitContext = (GitSubscriberResourceMappingContext) context;
			GitSynchronizeDataSet gsds = gitContext.getSyncData();
			GitSynchronizeData data = gsds.getData(resource.getProject());

			if (data != null) {
				GitModelObject object = null;
				try {
					object = GitModelObject.createRoot(data);
				} catch (IOException e) {
					Activator.logError(e.getMessage(), e);
				}

				if (object != null) {
					ResourceMapping rm = AdapterUtils.adapt(object, ResourceMapping.class);
					return new ResourceMapping[] { rm };
				}
			}
		}

		return super.getMappings(resource, context, monitor);
	}
}

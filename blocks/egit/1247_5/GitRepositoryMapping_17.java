package org.eclipse.egit.ui.internal.synchronize.mapping;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.egit.ui.internal.synchronize.GitChangeSetModelProvider;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelBlob;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelCommit;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelObject;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelRepository;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelTree;

public abstract class GitObjectMapping extends ResourceMapping {

	private final GitModelObject object;

	public static ResourceMapping create(GitModelObject object) {
		if (object instanceof GitModelBlob)
			return new GitBlobMapping((GitModelBlob) object);
		if (object instanceof GitModelTree)
			return new GitTreeMapping((GitModelTree) object);
		if (object instanceof GitModelCommit)
			return new GitCommitMapping((GitModelCommit) object);
		if (object instanceof GitModelRepository)
			return new GitRepositoryMapping((GitModelRepository) object);

		return null;
	}

	protected GitObjectMapping(GitModelObject parent) {
		this.object = parent;
	}

	@Override
	public boolean contains(ResourceMapping mapping) {
		if (mapping.getModelProviderId().equals(getModelProviderId())) {
			GitModelObject obj = (GitModelObject) mapping.getModelObject();
			return obj.getRepository().equals(object.getRepository());
		}

		return false;
	}

	@Override
	public Object getModelObject() {
		return object;
	}

	@Override
	public String getModelProviderId() {
		return GitChangeSetModelProvider.ID;
	}

	@Override
	public IProject[] getProjects() {
		return object.getProjects();
	}

}

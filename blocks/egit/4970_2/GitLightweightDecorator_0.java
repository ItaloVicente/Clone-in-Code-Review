package org.eclipse.egit.ui.internal.decorators;

import static org.eclipse.jgit.lib.Repository.stripWorkDir;

import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkingSet;

public class DecoratableResourceMapping extends DecoratableResource {

	public static final int RESOURCE_MAPPING = 0x10;

	private ResourceMapping mapping;

	public DecoratableResourceMapping(ResourceMapping mapping) {
		super(null); // no resource ...

		this.mapping = mapping;
		IProject[] projects = mapping.getProjects();

		if(projects == null || projects.length == 0)
			return;

		for(IProject prj : projects) {
			RepositoryMapping repoMapping = RepositoryMapping.getMapping(prj);
			if(repoMapping == null)
				continue;

			IndexDiffData diffData = GitLightweightDecorator.getIndexDiffDataOrNull(prj);
			if(diffData == null)
				continue;

			tracked = true;

			String repoRelative = makeRepoRelative(repoMapping.getRepository(), prj) + "/"; //$NON-NLS-1$

			Set<String> modified = diffData.getModified();
			Set<String> conflicting = diffData.getConflicting();

			if(containsPrefix(modified, repoRelative))
				dirty = true;

			if(containsPrefix(conflicting, repoRelative))
				conflicts = true;
		}
	}

	public int getType() {
		return RESOURCE_MAPPING;
	}

	public String getName() {
		if(mapping.getModelObject() instanceof IWorkingSet) {
			IWorkingSet ws = (IWorkingSet)mapping.getModelObject();
			return ws.getLabel();
		}

		return "<unknown>"; //$NON-NLS-1$
	}

	private String makeRepoRelative(Repository repository, IResource res) {
		return stripWorkDir(repository.getWorkTree(), res.getLocation()
				.toFile());
	}

	private boolean containsPrefix(Set<String> collection, String prefix) {
		if (prefix.length() == 1 && !collection.isEmpty())
			return true;

		for (String path : collection)
			if (path.startsWith(prefix))
				return true;
		return false;
	}
}

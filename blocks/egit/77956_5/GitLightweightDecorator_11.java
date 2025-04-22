
package org.eclipse.egit.ui.internal.decorators;

import static org.eclipse.jgit.lib.Repository.stripWorkDir;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.internal.indexdiff.IndexDiffData;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.egit.ui.internal.resources.ResourceStateFactory;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.ui.IWorkingSet;

public class DecoratableWorkingSet extends DecoratableResource {

	public static final int WORKING_SET = 0x9999;

	private ResourceMapping mapping;

	public DecoratableWorkingSet(ResourceMapping mapping) throws IOException {
		super(null); // no resource ...

		this.mapping = mapping;
		IProject[] projects = mapping.getProjects();

		if(projects == null || projects.length == 0)
			return;

		Set<Repository> repositories = new HashSet<>(projects.length);

		for(IProject prj : projects) {
			RepositoryMapping repoMapping = RepositoryMapping.getMapping(prj);
			if(repoMapping == null)
				continue;

			IndexDiffData diffData = ResourceStateFactory.getInstance()
					.getIndexDiffDataOrNull(prj);
			if(diffData == null)
				continue;

			setTracked(true);

			Repository repository = repoMapping.getRepository();
			String repoRelative = makeRepoRelative(repository, prj);
			if (repoRelative == null) {
				continue;
			}
			repoRelative += "/"; //$NON-NLS-1$

			Set<String> modified = diffData.getModified();
			Set<String> conflicting = diffData.getConflicting();

			if(containsPrefix(modified, repoRelative))
				setDirty(true);

			if(containsPrefix(conflicting, repoRelative))
				setConflicts(true);

			repositories.add(repository);
		}

		DecoratableResourceMapping.decorateRepositoryInformation(this,
				repositories);
	}

	@Override
	public int getType() {
		return WORKING_SET;
	}

	@Override
	public String getName() {
		IWorkingSet ws = (IWorkingSet) mapping.getModelObject();
		return ws.getLabel();
	}

	@Nullable
	private String makeRepoRelative(Repository repository, IResource res) {
		if (repository.isBare()) {
			return null;
		}
		IPath location = res.getLocation();
		if (location == null) {
			return null;
		}
		return stripWorkDir(repository.getWorkTree(), location.toFile());
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

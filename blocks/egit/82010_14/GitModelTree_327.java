package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;

public class GitModelRoot {

	private final GitSynchronizeDataSet gsds;

	private GitModelObject[] children;

	public GitModelRoot(GitSynchronizeDataSet gsds) {
		this.gsds = gsds;
	}

	public GitSynchronizeDataSet getGsds() {
		return gsds;
	}

	public GitModelObject[] getChildren() {
		return getChildrenImpl();
	}

	public void dispose() {
		disposeOldChildren();
	}

	private GitModelObject[] getChildrenImpl() {
		List<GitModelObject> result = new ArrayList<>();
		try {
			if (gsds.size() == 1) {
				GitSynchronizeData gsd = gsds.iterator().next();
				GitModelRepository repoModel = new GitModelRepository(gsd);

				return repoModel.getChildren();
			} else
				for (GitSynchronizeData data : gsds) {
					GitModelRepository repoModel = new GitModelRepository(data);
					if (repoModel.getChildren().length > 0)
						result.add(repoModel);
				}
		} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
		}
		disposeOldChildren();
		children = result.toArray(new GitModelObject[result.size()]);

		return children;
	}

	private void disposeOldChildren() {
		if (children == null)
			return;
		for (GitModelObject child : children)
			child.dispose();
	}

}

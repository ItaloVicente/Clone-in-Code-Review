package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeDataSet;

public class GitModelRoot {

	private final GitSynchronizeDataSet gsds;

	public GitModelRoot(GitSynchronizeDataSet gsds) {
		this.gsds = gsds;
	}

	public GitSynchronizeDataSet getGsds() {
		return gsds;
	}

	public GitModelRepository[] getChildren() {
		List<GitModelRepository> restult = new ArrayList<GitModelRepository>();
		for (GitSynchronizeData data : gsds) {
			try {
				restult.add(new GitModelRepository(data));
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
			}
		}

		return restult.toArray(new GitModelRepository[restult.size()]);
	}

}

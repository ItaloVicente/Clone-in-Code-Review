package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitModelCacheTree extends GitModelTree {

	private final Map<String, GitModelObject> cacheTreeMap;

	public GitModelCacheTree(GitModelObjectContainer parent, RevCommit commit,
			ObjectId repoId, ObjectId cacheId, String name) throws IOException {
		super(parent, commit, repoId, repoId, cacheId, name);
		cacheTreeMap = new HashMap<String, GitModelObject>();
	}

	void addChild(ObjectId repoId, ObjectId cacheId, String path)
			throws IOException {
		String[] entrys = path.split("/"); //$NON-NLS-1$
		String pathKey = entrys[0];
		if (entrys.length > 1) {
			GitModelCacheTree cacheEntry = (GitModelCacheTree) cacheTreeMap
					.get(pathKey);
			if (cacheEntry == null) {
				cacheEntry = new GitModelCacheTree(this, remoteCommit, repoId,
						cacheId, pathKey);
				cacheTreeMap.put(pathKey, cacheEntry);
			}
			cacheEntry.addChild(repoId, cacheId,
					path.substring(path.indexOf('/') + 1));
		} else {
			cacheTreeMap.put(pathKey, new GitModelBlob(this, remoteCommit,
					repoId, repoId, cacheId, pathKey));
		}
	}

	@Override
	protected GitModelObject[] getChildrenImpl() {
		Collection<GitModelObject> values = cacheTreeMap.values();

		return values.toArray(new GitModelObject[values.size()]);
	}

}

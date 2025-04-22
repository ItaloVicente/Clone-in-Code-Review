package org.eclipse.egit.ui.internal.repository.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class BranchHierarchyNode extends RepositoryTreeNode<IPath> {

	public BranchHierarchyNode(RepositoryTreeNode parent,
			Repository repository, IPath path) {
		super(parent, RepositoryTreeNodeType.BRANCHHIERARCHY, repository, path.addTrailingSeparator());
	}

	public List<IPath> getChildPaths() throws IOException {
		List<IPath> result = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				int segmentDiff = myPath.segmentCount()
						- getObject().segmentCount();
				if (segmentDiff > 1) {
					IPath newPath = getObject().append(
							myPath.segment(getObject().segmentCount()));
					if (!result.contains(newPath))
						result.add(newPath);
				}
			}
		}
		return result;
	}

	public List<Ref> getChildRefs() throws IOException {
		List<Ref> childRefs = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				int segmentDiff = myPath.segmentCount()
						- getObject().segmentCount();
				if (segmentDiff == 1) {
					Ref ref = getRepository()
							.findRef(myPath.toPortableString());
					childRefs.add(ref);
				}
			}
		}
		return childRefs;
	}

	public List<Ref> getChildRefsRecursive() throws IOException {
		List<Ref> childRefs = new ArrayList<>();
		for (IPath myPath : getPathList()) {
			if (getObject().isPrefixOf(myPath)) {
				Ref ref = getRepository().exactRef(myPath.toPortableString());
				childRefs.add(ref);
			}
		}
		return childRefs;
	}

	private List<IPath> getPathList() throws IOException {
		List<IPath> result = new ArrayList<>();
		Map<String, Ref> refsMap = getRepository().getRefDatabase().getRefs(
				getObject().toPortableString()); // getObject() returns path ending with /
		for (Map.Entry<String, Ref> entry : refsMap.entrySet()) {
			if (entry.getValue().isSymbolic())
				continue;
			result.add(getObject().append(new Path(entry.getKey())));
		}
		return result;
	}
}

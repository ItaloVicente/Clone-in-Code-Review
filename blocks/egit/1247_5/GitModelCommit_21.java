package org.eclipse.egit.ui.internal.synchronize.model;

import java.io.IOException;

import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.graphics.Image;

public class GitModelBlob extends GitModelCommit implements ICompareInput {

	private final String name;

	private final ObjectId baseId;

	private final ObjectId remoteId;

	private final ObjectId ancestorId;

	private final IPath location;

	private final String gitPath;

	private static final GitModelObject[] empty = new GitModelObject[0];

	public GitModelBlob(GitModelObject parent, RevCommit commit,
			ObjectId ancestorId, ObjectId baseId, ObjectId remoteId, String name)
			throws IOException {
		super(parent, commit);
		this.name = name;
		this.baseId = baseId;
		this.remoteId = remoteId;
		this.ancestorId = ancestorId;
		location = getParent().getLocation().append(name);
		gitPath = Repository.stripWorkDir(getRepository().getWorkTree(),
				getLocation().toFile());
	}

	@Override
	public GitModelObject[] getChildren() {
		return empty;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IProject[] getProjects() {
		return getParent().getProjects();
	}

	@Override
	public IPath getLocation() {
		return location;
	}

	public Image getImage() {
		return null;
	}

	public int getKind() {
		return Differencer.CONFLICTING;
	}

	public ITypedElement getAncestor() {
		return CompareUtils.getFileRevisionTypedElement(gitPath,
				getAncestorCommit(), getRepository(), ancestorId);
	}

	public ITypedElement getLeft() {
		if (getBaseCommit() != null && baseId != null)
			return CompareUtils.getFileRevisionTypedElement(gitPath,
					getBaseCommit(), getRepository(), baseId);

		return null;
	}

	public ITypedElement getRight() {
		return CompareUtils.getFileRevisionTypedElement(gitPath,
				getRemoteCommit(), getRepository(), remoteId);
	}

	public void addCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void removeCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void copy(boolean leftToRight) {
	}

}

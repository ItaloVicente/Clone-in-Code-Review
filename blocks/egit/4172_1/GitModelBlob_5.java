package org.eclipse.egit.ui.internal.synchronize.compare;

import static org.eclipse.compare.structuremergeviewer.Differencer.CHANGE;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.ui.UIText;
import org.eclipse.egit.ui.internal.CompareUtils;
import org.eclipse.egit.ui.internal.FileRevisionTypedElement;
import org.eclipse.egit.ui.internal.LocalResourceTypedElement;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.team.ui.mapping.ISynchronizationCompareInput;
import org.eclipse.team.ui.mapping.SaveableComparison;

public class GitWorkspaceCompareInput implements ISynchronizationCompareInput {

	private final String gitPath;

	private final Repository repo;

	private final ObjectId baseId;

	private final ObjectId remoteId;

	private final RevCommit baseCommit;

	private final RevCommit remoteCommit;

	public GitWorkspaceCompareInput(Repository repo, RevCommit baseCommit, ObjectId baseId, RevCommit remoteCommit, ObjectId remoteId, String gitPath) {
		this.repo = repo;
		this.baseId = baseId;
		this.gitPath = gitPath;
		this.remoteId = remoteId;
		this.baseCommit = baseCommit;
		this.remoteCommit = remoteCommit;
	}

	public String getName() {
		int lastSeparator = gitPath.indexOf("/"); //$NON-NLS-1$
		if (lastSeparator > -1)
			return gitPath.substring(lastSeparator + 1, gitPath.length());
		else
			return gitPath;
	}

	public Image getImage() {
		return null;
	}

	public int getKind() {
		return CHANGE;
	}

	public ITypedElement getAncestor() {
		return getRight();
	}

	public ITypedElement getLeft() {
		return CompareUtils.getFileRevisionTypedElement(gitPath, baseCommit,
				repo, baseId);
	}

	public ITypedElement getRight() {
		return CompareUtils.getFileRevisionTypedElement(gitPath, remoteCommit,
				repo, remoteId);
	}

	public void addCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void removeCompareInputChangeListener(
			ICompareInputChangeListener listener) {
	}

	public void copy(boolean leftToRight) {
	}

	public SaveableComparison getSaveable() {
		return null;
	}

	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		configuration.setLeftLabel(getFileRevisionLabel(getLeft()));
		configuration.setRightLabel(getFileRevisionLabel(getRight()));

		configuration.setLeftEditable(true);
		configuration.setRightEditable(false);
	}

	public String getFullPath() {
		return gitPath;
	}

	public boolean isCompareInputFor(Object obj) {
		return false;
	}

	private String getFileRevisionLabel(ITypedElement element) {
		if (element instanceof FileRevisionTypedElement) {
			FileRevisionTypedElement castElement = (FileRevisionTypedElement) element;
			return NLS.bind(
					UIText.GitCompareFileRevisionEditorInput_RevisionLabel,
					new Object[] {
							element.getName(),
							CompareUtils.truncatedRevision(castElement
									.getContentIdentifier()),
							castElement.getAuthor() });
		} else if (element instanceof LocalResourceTypedElement)
			return NLS.bind(
					UIText.GitCompareFileRevisionEditorInput_LocalVersion,
					element.getName());
		else
			return element.getName();
	}

}

package org.eclipse.egit.ui.internal;

import java.io.IOException;

import org.eclipse.compare.CompareEditorInput;
import org.eclipse.compare.ITypedElement;
import org.eclipse.egit.core.internal.storage.GitFileRevision;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.history.IFileRevision;
import org.eclipse.team.internal.ui.history.FileRevisionTypedElement;

public class CompareUtils {

	public static ITypedElement getFileRevisionTypedElement(
			final String gitPath, final RevCommit commit, final Repository db) {
		return getFileRevisionTypedElement(gitPath, commit, db, null);
	}

	public static ITypedElement getFileRevisionTypedElement(
			final String gitPath, final RevCommit commit, final Repository db,
			ObjectId blobId) {
		ITypedElement right = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
				NLS
						.bind(UIText.GitHistoryPage_FileNotInCommit, getName(gitPath),
								commit));

		try {
			IFileRevision nextFile = getFileRevision(gitPath, commit, db, blobId);
			if (nextFile != null)
				right = new FileRevisionTypedElement(nextFile);
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					gitPath, commit.getId()), e);
		}
		return right;
	}

	private static String getName(String gitPath) {
		final int last = gitPath.lastIndexOf('/');
		return last >= 0 ? gitPath.substring(last + 1) : gitPath;
	}

	public static IFileRevision getFileRevision(final String gitPath,
			final RevCommit commit, final Repository db, ObjectId blobId)
			throws IOException {

		TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
		if (w != null) {
			final IFileRevision fileRevision = GitFileRevision.inCommit(db,
					commit, gitPath, blobId);
			return fileRevision;
		}
		return null;
	}


}

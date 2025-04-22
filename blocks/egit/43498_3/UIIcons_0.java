package org.eclipse.egit.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jgit.revwalk.RevCommit;

public interface ICommitValidator {

	public IStatus validate(RevCommit commit);

}

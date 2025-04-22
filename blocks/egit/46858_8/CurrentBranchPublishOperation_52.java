package org.eclipse.egit.gitflow.op;

import static java.lang.String.format;
import static org.eclipse.egit.gitflow.Activator.error;

import java.io.IOException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.egit.core.op.TagOperation;
import org.eclipse.egit.gitflow.GitFlowRepository;
import org.eclipse.egit.gitflow.WrongGitFlowStateException;
import org.eclipse.egit.gitflow.internal.CoreText;
import org.eclipse.jgit.lib.TagBuilder;
import org.eclipse.jgit.revwalk.RevCommit;

abstract public class AbstractVersionFinishOperation extends GitFlowOperation {
	protected String versionName;

	public AbstractVersionFinishOperation(GitFlowRepository repository,
			String versionName) {
		super(repository);
		this.versionName = versionName;
	}

	protected void safeCreateTag(IProgressMonitor monitor, String tagName,
			String tagMessage) throws CoreException {
		RevCommit head;
		try {
			head = repository.findHead();
		} catch (WrongGitFlowStateException e) {
			throw new CoreException(error(e));
		}
		RevCommit commitForTag;
		try {
			commitForTag = repository.findCommitForTag(versionName);
			if (commitForTag == null) {
				createTag(monitor, head, tagName, tagMessage);
			} else if (!head.equals(commitForTag)) {
				throw new CoreException(error(format(
						CoreText.AbstractVersionFinishOperation_tagNameExists,
						versionName)));
			}
		} catch (IOException e) {
			throw new CoreException(error(e));
		}
	}

	protected void createTag(IProgressMonitor monitor, RevCommit head,
			String name, String message) throws CoreException {
		TagBuilder tag = new TagBuilder();
		tag.setTag(name);
		tag.setMessage(message);
		tag.setObjectId(head);
		new TagOperation(repository.getRepository(), tag, false)
				.execute(monitor);
	}
}

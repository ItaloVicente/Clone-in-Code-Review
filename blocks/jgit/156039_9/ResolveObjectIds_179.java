package org.eclipse.jgit.niofs.internal.op.commands;

import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;

public class RemoveRemote {

	final PathUtil pathUtil = new PathUtil();
	private Git git;
	private String ref;
	private String remote;

	public RemoveRemote(final Git git

		this.git = git;
		this.ref = ref;
		this.remote = remote;
	}

	public void execute() {
		try {
			git.getRepository().getConfig().unsetSection("remote"
			git.getRepository().getConfig().save();
			RefUpdate updateRef = git.getRepository().updateRef(ref
			updateRef.setRefLogMessage(ref + " packed-ref deleted"
			updateRef.setForceUpdate(true);
			updateRef.delete();
		} catch (Exception e) {
			throw new GitException("Error when trying to remove remote"
		}
	}
}

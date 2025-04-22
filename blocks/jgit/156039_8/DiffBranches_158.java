package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.niofs.internal.op.GitImpl;

public class DeleteBranch {

	private final GitImpl git;
	private final Ref branch;

	public DeleteBranch(final GitImpl git
		this.git = git;
		this.branch = branch;
	}

	public void execute() throws IOException {
		try {
			git._branchDelete().setBranchNames(branch.getName()).setForce(true).call();
		} catch (final GitAPIException e) {
			throw new IOException(e);
		}
	}
}

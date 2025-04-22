package org.eclipse.jgit.niofs.internal;

import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.niofs.internal.op.Git;

public class JGitFileSystemLock extends FileSystemLock {

	public JGitFileSystemLock(Git git

		super(git.getRepository().getDirectory()
	}
}

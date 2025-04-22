

package org.eclipse.jgit.niofs.internal;

import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.niofs.internal.op.Git;

public class JGitFileSystemLock extends FileSystemLock {

    public JGitFileSystemLock(Git git
                              TimeUnit t
                              long duration) {

        super(git.getRepository().getDirectory()
              "af.lock"
              t
              duration);
    }
}

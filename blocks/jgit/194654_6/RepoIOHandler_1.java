package org.eclipse.jgit.util;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;

public interface InCoreSupport {

  public static RepoIOHandler initRepoIOHandlerForLocal(Repository local
      DirCache dirCache) {
    return new RepoIOHandler(local
  }

  public static RepoIOHandler initRepoIOHandlerForRemote(Repository remote
      DirCache dirCache
    return new RepoIOHandler(remote
  }
}

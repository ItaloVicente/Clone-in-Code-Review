package org.eclipse.jgit.util;

import java.io.IOException;
import java.util.Optional;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;

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

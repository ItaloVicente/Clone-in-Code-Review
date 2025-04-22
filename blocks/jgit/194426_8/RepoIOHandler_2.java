package org.eclipse.jgit.util;

import java.io.IOException;
import org.eclipse.jgit.attributes.Attributes;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.TreeWalk;

public interface InCoreSupport {

  static RepoIOHandler initRepoIOHandler(
      Repository repo
      boolean inCore
      ObjectInserter oi
      ObjectReader reader
      Attributes attributes)
      throws IOException {
    String smudgeCommand = null;
    if (!inCore) {
      TreeWalk walk = new TreeWalk(repo);
      smudgeCommand = walk.getSmudgeCommand(attributes);
    }
    return new RepoIOHandler(
        repo
  }
}

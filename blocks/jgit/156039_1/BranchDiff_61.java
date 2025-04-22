
package org.eclipse.jgit.niofs.fs;

import java.nio.file.Path;

public interface WatchContext {

    Path getPath();

    Path getOldPath();

    String getSessionId();

    String getMessage();

    String getUser();
}

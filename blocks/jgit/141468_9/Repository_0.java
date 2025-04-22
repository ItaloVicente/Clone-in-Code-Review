
package org.eclipse.jgit.internal.storage.dfs;

import org.eclipse.jgit.lib.RepositoryInterface;

import java.io.IOException;

public interface DfsRepositoryInterface extends RepositoryInterface {

	DfsRepositoryDescription getDescription();

	boolean exists() throws IOException;
}

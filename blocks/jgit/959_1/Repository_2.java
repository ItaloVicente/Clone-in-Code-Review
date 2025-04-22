
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;

public class FileRepositoryBuilder extends
		BaseRepositoryBuilder<FileRepositoryBuilder
	@Override
	public FileRepository build() throws IOException {
		return new FileRepository(setup());
	}
}

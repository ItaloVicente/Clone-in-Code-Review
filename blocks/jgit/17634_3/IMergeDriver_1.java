package org.eclipse.jgit.merge;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;

public class BinaryMergeDriver implements IMergeDriver {
	public int merge(Repository repository
			String[] commitNames)
			throws IOException {
		return 1;
	}

	public String getName() {
		return JGitText.get().binaryMergeDriverName;
	}
}

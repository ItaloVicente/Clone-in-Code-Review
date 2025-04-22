package org.eclipse.jgit.merge;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;

public interface IMergeDriver {
	static final int OK = 0;

	int merge(Repository repository
			String[] commitNames)
			throws IOException;

	String getName();
}

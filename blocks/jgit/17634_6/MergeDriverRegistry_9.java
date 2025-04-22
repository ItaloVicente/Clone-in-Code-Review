package org.eclipse.jgit.merge;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;

public interface MergeDriver {
	boolean merge(Repository repository
			String[] commitNames)
			throws IOException;

	String getName();
}

package org.eclipse.jgit.merge;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.lib.Config;

public interface MergeDriver {
	boolean merge(Config configuration
			InputStream base
			throws IOException;

	String getName();
}

package org.eclipse.jgit.attributes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.lib.Repository;

public interface FilterCommandFactory {
	public FilterCommand create(Repository db
			OutputStream out) throws IOException;

}

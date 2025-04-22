
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.List;

public interface ReflogReader {

	public abstract ReflogEntry getLastEntry() throws IOException;

	public abstract List<ReflogEntry> getReverseEntries() throws IOException;

	public abstract ReflogEntry getReverseEntry(int number) throws IOException;

	public abstract List<ReflogEntry> getReverseEntries(int max)
			throws IOException;

}

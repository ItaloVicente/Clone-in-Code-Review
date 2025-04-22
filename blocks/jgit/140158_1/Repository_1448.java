
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.List;

public interface ReflogReader {

	ReflogEntry getLastEntry() throws IOException;

	List<ReflogEntry> getReverseEntries() throws IOException;

	ReflogEntry getReverseEntry(int number) throws IOException;

	List<ReflogEntry> getReverseEntries(int max) throws IOException;
}

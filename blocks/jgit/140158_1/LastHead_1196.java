
package org.eclipse.jgit.fnmatch;

import java.util.List;

interface Head {
	List<Head> getNextHeads(char c);
}

package org.eclipse.egit.core.internal.indexdiff;

import org.eclipse.jgit.lib.Repository;

public interface IndexDiffChangedListener {

	void indexDiffChanged(Repository repository, IndexDiffData indexDiffData);
}

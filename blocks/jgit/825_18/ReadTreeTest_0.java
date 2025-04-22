package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;

public class DirCacheCheckoutTest extends ReadTreeTest {
	private DirCacheCheckout dco;
	@Override
	public void prescanTwoTrees(Tree head
			throws IllegalStateException
		DirCache dc = db.lockDirCache();
		try {
			dco = new DirCacheCheckout(db
			dco.preScanTwoTrees();
		} finally {
			dc.unlock();
		}
	}

	@Override
	public void checkout() throws IOException {
		DirCache dc = db.lockDirCache();
		try {
			dco = new DirCacheCheckout(db
			dco.checkout();
		} finally {
			dc.unlock();
		}
	}

	@Override
	public List<String> getRemoved() {
		return dco.getRemoved();
	}

	@Override
	public Map<String
		return dco.getUpdated();
	}

	@Override
	public List<String> getConflicts() {
		return dco.getConflicts();
	}
}

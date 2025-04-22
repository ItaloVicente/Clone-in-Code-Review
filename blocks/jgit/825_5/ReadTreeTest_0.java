package org.eclipse.jgit.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;

public class DirCacheCheckoutTest extends ReadTreeTest {
	private DirCacheCheckout dco;
	@Override
	public void prescanTwoTrees(Tree head
			throws IllegalStateException
		DirCache dc = DirCache.lock(db);
		try {
			dco = new DirCacheCheckout(db
			dco.preScanTwoTrees();
		} finally {
			dc.unlock();
		}
	}

	@Override
	public void checkout() throws IOException {
		DirCache dc = DirCache.lock(db);
		try {
			dco = new DirCacheCheckout(db
			dco.checkout();
		} finally {
			dc.unlock();
		}
	}

	@Override
	public ArrayList<String> getRemoved() {
		return dco.getRemoved();
	}

	@Override
	public HashMap<String
		return dco.getUpdated();
	}

	@Override
	public ArrayList<String> getConflicts() {
		return dco.getConflicts();
	}
}

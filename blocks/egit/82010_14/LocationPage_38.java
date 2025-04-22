package org.eclipse.egit.ui.internal.history;

import org.eclipse.jgit.revwalk.RevObject;

public interface IFindListener {

	void itemAdded(int index, RevObject rev);

	void cleared();
}

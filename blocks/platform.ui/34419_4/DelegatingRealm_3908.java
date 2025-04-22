package org.eclipse.jface.databinding.conformance.util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.databinding.observable.Realm;

public class CurrentRealm extends Realm {
	private boolean current;
	private List<Runnable> queue = new LinkedList<Runnable>();

	public CurrentRealm() {
		this(false);
	}

	public CurrentRealm(boolean current) {
		this.current = current;
	}

	@Override
	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
		processTasks();
	}

	@Override
	protected void syncExec(Runnable runnable) {
		super.syncExec(runnable);
	}

	private void processTasks() {
		if (isCurrent()) {
			for (Iterator<Runnable> it = queue.iterator(); it.hasNext();) {
				Runnable task = it.next();
				it.remove();
				safeRun(task);
			}
		}
	}

	@Override
	public void asyncExec(Runnable runnable) {
		queue.add(runnable);
	}

	protected static Realm setDefault(Realm realm) {
		return Realm.setDefault(realm);
	}
}

package org.eclipse.jface.databinding.conformance.util;

import org.eclipse.core.databinding.observable.Realm;

public class DelegatingRealm extends CurrentRealm {
	private Realm realm;

	public DelegatingRealm(Realm realm) {
		this.realm = realm;
	}

	@Override
	protected void syncExec(Runnable runnable) {
		realm.exec(runnable);
	}

	@Override
	public void asyncExec(Runnable runnable) {
		realm.asyncExec(runnable);
	}
}

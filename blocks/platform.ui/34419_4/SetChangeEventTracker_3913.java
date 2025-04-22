
package org.eclipse.jface.databinding.conformance.util;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.AssertionFailedException;
import org.junit.Assert;

public class RealmTester {

	public static void setDefault(Realm realm) {
		CurrentRealm.setDefault(realm);
	}

	public static void exerciseCurrent(Runnable runnable) {
		CurrentRealm previousRealm = (CurrentRealm) Realm.getDefault();
		CurrentRealm realm = new CurrentRealm();
		setDefault(realm);

		try {
			realm.setCurrent(true);
			if (previousRealm != null) {
				previousRealm.setCurrent(true);
			}

			try {
				runnable.run();
			} catch (AssertionFailedException e) {
				Assert.fail("Correct realm, exception should not have been thrown");
			}

			realm.setCurrent(false);
			if (previousRealm != null) {
				previousRealm.setCurrent(false);
			}

			try {
				runnable.run();
				Assert.fail("Incorrect realm, exception should have been thrown");
			} catch (AssertionFailedException e) {
			}
		} finally {
			setDefault(previousRealm);
		}
	}

	public static void exerciseCurrent(Runnable runnable, CurrentRealm realm) {
		realm.setCurrent(true);

		try {
			runnable.run();
		} catch (AssertionFailedException e) {
			Assert.fail("Correct realm, exception should not have been thrown");
		}

		realm.setCurrent(false);

		try {
			runnable.run();
			Assert.fail("Incorrect realm, exception should have been thrown");
		} catch (AssertionFailedException e) {
		}
	}
}

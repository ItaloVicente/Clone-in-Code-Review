/*******************************************************************************
 * Copyright (c) 2007, 2009 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 210115)
 *     Matthew Hall - bugs 249526, 251424
 ******************************************************************************/

package org.eclipse.core.tests.databinding.observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.eclipse.core.databinding.observable.AbstractObservable;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.util.ILogger;
import org.eclipse.core.databinding.util.Policy;
import org.eclipse.core.internal.databinding.IdentitySet;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.databinding.conformance.util.CurrentRealm;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class ObservableTrackerTest extends AbstractDefaultRealmTestCase {
	public void testRunAndMonitor_GetterCalled() throws Exception {
		final IObservable observable = new ObservableStub();
		IObservable[] result = ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.getterCalled(observable);
			}
		}, null, null);
		assertEquals(1, result.length);
		assertSame(observable, result[0]);
	}

	public void testGetterCalled_ObservableDisposed() throws Exception {
		try {
			IObservable observable = new ObservableStub();
			observable.dispose();

			ObservableTracker.getterCalled(observable);

			fail("expected AssertionFailedException");
		} catch (AssertionFailedException expected) {
		}
	}

	public void testGetterCalled_ObservableRealmNotCurrent() throws Exception {
		try {
			IObservable observable = new ObservableStub(new CurrentRealm(false));

			ObservableTracker.getterCalled(observable);

			fail("expected AssertionFailedException");
		} catch (AssertionFailedException expected) {
		}
	}

	public void testRunAndCollect() throws Exception {
		final IObservable[] created = new IObservable[1];
		IObservable[] collected = ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				created[0] = new ObservableStub();
			}
		});
		assertEquals(1, collected.length);
		assertSame(created[0], collected[0]);
	}

	public void testRunAndIgnore_RunAndMonitor() throws Exception {
		final IObservable observable = new ObservableStub();
		IObservable[] result = ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.runAndIgnore(new Runnable() {
					@Override
					public void run() {
						ObservableTracker.getterCalled(observable);
					}
				});
			}
		}, null, null);
		assertEquals(0, result.length);
	}

	public void testRunAndIgnore_RunAndCollect() throws Exception {
		IObservable[] result = ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.runAndIgnore(new Runnable() {
					@Override
					public void run() {
						new ObservableStub();
					}
				});
			}
		});
		assertEquals(0, result.length);
	}

	public void testSetIgnore_RunAndMonitor() throws Exception {
		final IObservable observable = new ObservableStub();
		IObservable[] result = ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				ObservableTracker.getterCalled(observable);
				ObservableTracker.setIgnore(false);
			}
		}, null, null);
		assertEquals(0, result.length);
	}

	public void testSetIgnore_RunAndCollect() throws Exception {
		IObservable[] result = ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				new ObservableStub();
				ObservableTracker.setIgnore(false);
			}
		});
		assertEquals(0, result.length);
	}

	public void testSetIgnore_Nested_RunAndCollect() throws Exception {
		final List<ObservableStub> list = new ArrayList<ObservableStub>();

		Set collected = new IdentitySet(Arrays.asList(ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				ObservableTracker.setIgnore(true);
				ObservableTracker.setIgnore(false);
				ObservableTracker.setIgnore(false);
			}
		})));

		Set<ObservableStub> expected = new IdentitySet();
		expected.add(list.get(0));
		expected.add(list.get(4));
		assertEquals(expected, collected);
	}

	public void testSetIgnore_Nested_RunAndMonitor() throws Exception {
		final IObservable[] observables = { new ObservableStub(), new ObservableStub(), new ObservableStub(),
				new ObservableStub(), new ObservableStub() };

		Set result = new IdentitySet(Arrays.asList(ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
				ObservableTracker.setIgnore(true);
				ObservableTracker.setIgnore(false);
				ObservableTracker.setIgnore(false);
			}
		}, null, null)));

		Set<IObservable> expected = new IdentitySet();
		expected.add(observables[0]);
		expected.add(observables[4]);
		assertEquals(expected, result);
	}

	public void testSetIgnore_RunAndMonitor_UnmatchedIgnore_LogsError() {
		final List<IStatus> log = new ArrayList<IStatus>();
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				log.add(status);
			}
		});

		ObservableTracker.runAndMonitor(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
			}
		}, null, null);

		assertEquals(1, log.size());
		IStatus status = log.get(0);
		assertEquals(IStatus.ERROR, status.getSeverity());
		assertTrue(status.getMessage().indexOf("setIgnore") != -1);
	}

	public void testSetIgnore_RunAndCollect_UnmatchedIgnore_LogsError() {
		final List<IStatus> log = new ArrayList<IStatus>();
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				log.add(status);
			}
		});

		ObservableTracker.runAndCollect(new Runnable() {
			@Override
			public void run() {
				ObservableTracker.setIgnore(true);
			}
		});

		assertEquals(1, log.size());
		IStatus status = log.get(0);
		assertEquals(IStatus.ERROR, status.getSeverity());
		assertTrue(status.getMessage().indexOf("setIgnore") != -1);
	}

	public void testSetIgnore_UnmatchedUnignore() {
		try {
			ObservableTracker.setIgnore(false);
			fail("Expected IllegalStateException");
		} catch (IllegalStateException expected) {
		}
	}

	public static class ObservableStub extends AbstractObservable {
		public ObservableStub() {
			this(Realm.getDefault());
		}

		public ObservableStub(Realm realm) {
			super(realm);
		}

		@Override
		public boolean isStale() {
			return false;
		}
	}
}

/*******************************************************************************
 * Copyright (c) 2013, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     RenÃ© Brandstetter - Bug 419749
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 472654
 ******************************************************************************/

package org.eclipse.e4.ui.internal.workbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTrackerCustomizer;

/**
 * A simple {@link BundleTrackerCustomizer} which is able to resolve a bundle by its symbolic name.
 */
/*
 * TODO: This implementation can probably be removed after a switch to OSGi 6 which will have the
 * possibility to lookup bundles with the FrameworkWiring#findProviders(Requirement) method.
 */
final class BundleFinder implements BundleTrackerCustomizer<List<Bundle>> {

	/** Map of bundle symbolic name to the corresponding bundles (hint: different versions). */
	private final ConcurrentMap<String, List<Bundle>> trackedBundles = new ConcurrentHashMap<>();

	/**
	 * Resolves the latest bundle with the given bundle symbolic name.
	 * <p>
	 * The latest means the bundle with the highest version.
	 * </p>
	 * @param symbolicName
	 *            the bundle symbolic name
	 * @return the latest bundle with the given bundle symbolic name
	 */
	public Bundle findBundle(String symbolicName) {
		List<Bundle> bundlesWithSameSymName = trackedBundles.get(symbolicName);
		if (bundlesWithSameSymName == null)
			return null;

		List<Bundle> snapshot = new ArrayList<>(bundlesWithSameSymName);

		switch (snapshot.size()) {
		case 0:
			return null;
		case 1:
			return snapshot.get(0);
		default:
			Collections.sort(snapshot, VersionComperator.INSTANCE); // sort the snapshot by version
		}
	}

	@Override
	public List<Bundle> addingBundle(Bundle bundle, BundleEvent event) {
		String bundleSymName = bundle.getSymbolicName();
		if (bundleSymName == null) {
			return null;
		}

		List<Bundle> bundlesWithSameSymName = trackedBundles.get(bundleSymName);
		if (bundlesWithSameSymName == null) {
			bundlesWithSameSymName = new CopyOnWriteArrayList<>();

			if (trackedBundles.putIfAbsent(bundleSymName, bundlesWithSameSymName) != null) {
				bundlesWithSameSymName = trackedBundles.get(bundleSymName);
			}
		}

		bundlesWithSameSymName.add(bundle);

		return bundlesWithSameSymName;
	}

	@Override
	public void modifiedBundle(Bundle bundle, BundleEvent event, List<Bundle> bundlesWithSameSymName) {
	}

	@Override
	public void removedBundle(Bundle bundle, BundleEvent event, List<Bundle> bundlesWithSameSymName) {
		bundlesWithSameSymName.remove(bundle);
	}

	/**
	 * A simple {@link Comparator} which orders the bundles by their version in ascending order.
	 */
	private static final class VersionComperator implements Comparator<Bundle> {
		/** A Singleton instance of this {@link Comparator} (the compare is done state-less). */
		public static final Comparator<Bundle> INSTANCE = new VersionComperator();

		private VersionComperator() {
		}

		@Override
		public int compare(Bundle bundle1, Bundle bundle2) {
			if (bundle1 == null) {
			}

			if (bundle2 == null) {
			}

		}
	}
}

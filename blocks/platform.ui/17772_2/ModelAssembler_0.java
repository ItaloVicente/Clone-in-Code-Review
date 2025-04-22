
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

final class BundleFinder implements BundleTrackerCustomizer {

	private final ConcurrentMap<String, List<Bundle>> trackedBundles = new ConcurrentHashMap<String, List<Bundle>>();

	public Bundle findBundle(String symbolicName) {
		List<Bundle> bundlesWithSameSymName = trackedBundles.get(symbolicName);
		if (bundlesWithSameSymName == null)
			return null;

		List<Bundle> snapshot = new ArrayList<Bundle>(bundlesWithSameSymName);

		switch (snapshot.size()) {
		case 0:
			return null;
		case 1:
			return snapshot.get(0);
		default:
			Collections.sort(snapshot, VersionComperator.INSTANCE); // sort the snapshot by version
			return snapshot.get(0); // the highest is the first entry in the list
		}
	}

	public Object addingBundle(Bundle bundle, BundleEvent event) {
		String bundleSymName = bundle.getSymbolicName();

		List<Bundle> bundlesWithSameSymName = trackedBundles.get(bundleSymName);
		if (bundlesWithSameSymName == null) {
			bundlesWithSameSymName = new CopyOnWriteArrayList<Bundle>();

			if (trackedBundles.putIfAbsent(bundleSymName, bundlesWithSameSymName) != null) {
				bundlesWithSameSymName = trackedBundles.get(bundleSymName);
			}
		}

		bundlesWithSameSymName.add(bundle);

		return bundlesWithSameSymName;
	}

	public void modifiedBundle(Bundle bundle, BundleEvent event, Object object) {
	}

	public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
		List<?> bundlesWithSameSymName = (List<?>) object;
		bundlesWithSameSymName.remove(bundle);
	}

	private static final class VersionComperator implements Comparator<Bundle> {
		public static final Comparator<Bundle> INSTANCE = new VersionComperator();

		private VersionComperator() {
		}

		public int compare(Bundle bundle1, Bundle bundle2) {
			if (bundle1 == null) {
				return bundle2 == null ? 0 : 1; // null elements at the end of the list
			}

			if (bundle2 == null) {
				return -1; // null elements at the end of the list
			}

			return bundle2.getVersion().compareTo(bundle1.getVersion()); // newest version first
		}
	}
}

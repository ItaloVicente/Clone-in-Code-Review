package org.eclipse.e4.ui.internal.about;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.eclipse.jface.resource.ImageDescriptor;

import com.ibm.icu.text.Collator;

public abstract class AboutData {
	private String providerName;

	private String name;

	private String version;

	private String id;

	private String versionedId = null;

	protected AboutData(String providerName, String name, String version, String id) {
		this.providerName = providerName == null ? "" : providerName; //$NON-NLS-1$
		this.name = name == null ? "" : name; //$NON-NLS-1$
		this.version = version == null ? "" : version; //$NON-NLS-1$
		this.id = id == null ? "" : id; //$NON-NLS-1$
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getVersion() {
		return version;
	}

	public String getVersionedId() {
		if (versionedId == null) {
			versionedId = getId() + "_" + getVersion(); //$NON-NLS-1$
		}
		return versionedId;
	}

	private static void reverse(AboutData[] infos) {
		List<AboutData> infoList = Arrays.asList(infos);
		Collections.reverse(infoList);
		for (int i = 0; i < infos.length; ++i) {
			infos[i] = infoList.get(i);
		}
	}

	public static void sortByProvider(boolean reverse, AboutData[] infos) {
		if (reverse) {
			reverse(infos);
			return;
		}

		Arrays.sort(infos, new Comparator<AboutData>() {
			Collator collator = Collator.getInstance(Locale.getDefault());

			@Override
			public int compare(AboutData info1, AboutData info2) {

				String provider1 = info1.getProviderName();
				String provider2 = info2.getProviderName();

				if (!provider1.equals(provider2)) {
					return collator.compare(provider1, provider2);
				}

				return collator.compare(info1.getName(), info2.getName());
			}
		});
	}

	public static void sortByName(boolean reverse, AboutData[] infos) {
		if (reverse) {
			reverse(infos);
			return;
		}

		Arrays.sort(infos, new Comparator<AboutData>() {
			Collator collator = Collator.getInstance(Locale.getDefault());

			@Override
			public int compare(AboutData info1, AboutData info2) {
				return collator.compare(info1.getName(), info2.getName());
			}
		});
	}

	public static void sortByVersion(boolean reverse, AboutData[] infos) {
		if (reverse) {
			reverse(infos);
			return;
		}

		Arrays.sort(infos, new Comparator<AboutData>() {
			Collator collator = Collator.getInstance(Locale.getDefault());

			@Override
			public int compare(AboutData info1, AboutData info2) {

				String version1 = info1.getVersion();
				String version2 = info2.getVersion();

				if (!version1.equals(version2)) {
					return collator.compare(version1, version2);
				}

				return collator.compare(info1.getName(), info2.getName());
			}
		});
	}

	public static void sortById(boolean reverse, AboutData[] infos) {
		if (reverse) {
			reverse(infos);
			return;
		}

		Arrays.sort(infos, new Comparator<AboutData>() {
			Collator collator = Collator.getInstance(Locale.getDefault());

			@Override
			public int compare(AboutData info1, AboutData info2) {

				String id1 = info1.getId();
				String id2 = info2.getId();

				if (!id1.equals(id2)) {
					return collator.compare(id1, id2);
				}

				return collator.compare(info1.getName(), info2.getName());
			}
		});
	}

	protected static URL getURL(String value) {
		try {
			if (value != null) {
				return new URL(value);
			}
		} catch (IOException e) {
		}

		return null;
	}

	protected static ImageDescriptor getImage(URL url) {
		return url == null ? null : ImageDescriptor.createFromURL(url);
	}

	protected static ImageDescriptor getImage(String value) {
		return getImage(getURL(value));
	}
}

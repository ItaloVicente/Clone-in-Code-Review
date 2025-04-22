package org.eclipse.e4.ui.internal.about;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IBundleGroup;
import org.eclipse.core.runtime.IBundleGroupProvider;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.e4.ui.about.ISystemSummarySection;
import org.eclipse.e4.ui.internal.WorkbenchMessages;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

public class ConfigurationLogDefaultSection implements ISystemSummarySection {

	private static final String ECLIPSE_PROPERTY_PREFIX = "eclipse."; //$NON-NLS-1$

	@Override
	public void write(PrintWriter writer) {
		appendProperties(writer);
		appendFeatures(writer);
		appendRegistry(writer);
		appendUserPreferences(writer);
	}

	private void appendProperties(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_systemProperties);
		Properties properties = System.getProperties();
		SortedSet<Object> set = new TreeSet<Object>(new Comparator<Object>() {
			@Override
			public int compare(Object o1, Object o2) {
				String s1 = (String) o1;
				String s2 = (String) o2;
				return s1.compareTo(s2);
			}
		});
		set.addAll(properties.keySet());
		Iterator<Object> i = set.iterator();
		while (i.hasNext()) {
			String key = (String) i.next();
			String value = properties.getProperty(key);

			writer.print(key);
			writer.print('=');

			if (key.startsWith(ECLIPSE_PROPERTY_PREFIX)) {
				printEclipseProperty(writer, value);
			} else if (key.toUpperCase().indexOf("PASSWORD") != -1) { //$NON-NLS-1$
				for (int j = 0; j < value.length(); j++) {
					writer.print('*');
				}
				writer.println();
			} else {
				writer.println(value);
			}
		}
	}

	private static void printEclipseProperty(PrintWriter writer, String value) {
		String[] lines = AboutUtils.getArrayFromList(value, "\n"); //$NON-NLS-1$
		for (int i = 0; i < lines.length; ++i) {
			writer.println(lines[i]);
		}
	}

	private void appendFeatures(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_features);

		IBundleGroupProvider[] providers = Platform.getBundleGroupProviders();
		LinkedList<AboutBundleGroupData> groups = new LinkedList<AboutBundleGroupData>();
		if (providers != null) {
			for (int i = 0; i < providers.length; ++i) {
				IBundleGroup[] bundleGroups = providers[i].getBundleGroups();
				for (int j = 0; j < bundleGroups.length; ++j) {
					groups.add(new AboutBundleGroupData(bundleGroups[j]));
				}
			}
		}
		AboutBundleGroupData[] bundleGroupInfos = (AboutBundleGroupData[]) groups.toArray(new AboutBundleGroupData[0]);

		AboutData.sortById(false, bundleGroupInfos);

		for (int i = 0; i < bundleGroupInfos.length; ++i) {
			AboutBundleGroupData info = bundleGroupInfos[i];
			String[] args = new String[] { info.getId(), info.getVersion(), info.getName() };
			writer.println(NLS.bind(WorkbenchMessages.SystemSummary_featureVersion, args));
		}
	}

	private void appendRegistry(PrintWriter writer) {
		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_pluginRegistry);

		BundleContext bundleContext = FrameworkUtil.getBundle(getClass()).getBundleContext();
		Bundle[] bundles = bundleContext.getBundles();
		AboutBundleData[] bundleInfos = new AboutBundleData[bundles.length];

		for (int i = 0; i < bundles.length; ++i) {
			bundleInfos[i] = new AboutBundleData(bundles[i]);
		}

		AboutData.sortById(false, bundleInfos);

		for (int i = 0; i < bundleInfos.length; ++i) {
			AboutBundleData info = bundleInfos[i];
			String[] args = new String[] { info.getId(), info.getVersion(), info.getName(), info.getStateName() };
			writer.println(NLS.bind(WorkbenchMessages.SystemSummary_descriptorIdVersionState, args));
		}
	}

	private void appendUserPreferences(PrintWriter writer) {
		IPreferencesService service = Platform.getPreferencesService();
		IEclipsePreferences node = service.getRootNode();
		ByteArrayOutputStream stm = new ByteArrayOutputStream();
		try {
			service.exportPreferences(node, stm, null);
		} catch (CoreException e) {
			writer.println("Error reading preferences " + e.toString());//$NON-NLS-1$		
		}

		writer.println();
		writer.println(WorkbenchMessages.SystemSummary_userPreferences);

		BufferedReader reader = null;
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(stm.toByteArray());
			reader = new BufferedReader(new InputStreamReader(in, "8859_1")); //$NON-NLS-1$
			char[] chars = new char[8192];

			while (true) {
				int read = reader.read(chars);
				if (read <= 0) {
					break;
				}
				writer.write(chars, 0, read);
			}
		} catch (IOException e) {
			writer.println("Error reading preferences " + e.toString());//$NON-NLS-1$		
		}

	}
}

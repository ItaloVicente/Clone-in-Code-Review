
package org.eclipse.ui.internal;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.about.ISystemSummarySection;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

import com.ibm.icu.text.Collator;
import com.ibm.icu.text.DateFormat;

public final class ConfigurationInfo {

	public static String getBuildId() {
		return System.getProperty("eclipse.buildId", null); //$NON-NLS-1$
	}

	public static String getSystemSummary() {
		StringWriter out = new StringWriter();
		PrintWriter writer = new PrintWriter(out);
		writer.println(NLS.bind(WorkbenchMessages.SystemSummary_timeStamp,
				DateFormat
						.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL)
						.format(new Date())));

		ConfigurationInfo.appendExtensions(writer);
		writer.close();
		return out.toString();
	}

	private static void appendExtensions(PrintWriter writer) {
		IConfigurationElement[] configElements = getSortedExtensions(Platform
				.getExtensionRegistry().getConfigurationElementsFor(
						PlatformUI.PLUGIN_ID,
						IWorkbenchRegistryConstants.PL_SYSTEM_SUMMARY_SECTIONS));
		for (int i = 0; i < configElements.length; ++i) {
			IConfigurationElement element = configElements[i];

			Object obj = null;
			try {
				obj = WorkbenchPlugin.createExtension(element,
						IWorkbenchConstants.TAG_CLASS);
			} catch (CoreException e) {
				WorkbenchPlugin.log(
						"could not create class attribute for extension", //$NON-NLS-1$
						e.getStatus());
			}

			writer.println();
			writer.println(NLS.bind(
					WorkbenchMessages.SystemSummary_sectionTitle, element
							.getAttribute("sectionTitle"))); //$NON-NLS-1$

			if (obj instanceof ISystemSummarySection) {
				ISystemSummarySection logSection = (ISystemSummarySection) obj;
				logSection.write(writer);
			} else {
				writer.println(WorkbenchMessages.SystemSummary_sectionError);
			}
		}
	}

	public static IConfigurationElement[] getSortedExtensions(IConfigurationElement[] configElements) {

		Arrays.sort(configElements, new Comparator() {
			Collator collator = Collator.getInstance(Locale.getDefault());

			@Override
			public int compare(Object a, Object b) {
				IConfigurationElement element1 = (IConfigurationElement) a;
				IConfigurationElement element2 = (IConfigurationElement) b;

				String id1 = element1.getAttribute("id"); //$NON-NLS-1$
				String id2 = element2.getAttribute("id"); //$NON-NLS-1$

				if (id1 != null && id2 != null && !id1.equals(id2)) {
					return collator.compare(id1, id2);
				}

				String title1 = element1.getAttribute("sectionTitle"); //$NON-NLS-1$ 
				String title2 = element2.getAttribute("sectionTitle"); //$NON-NLS-1$

				if (title1 == null) {
					title1 = ""; //$NON-NLS-1$
				}
				if (title2 == null) {
					title2 = ""; //$NON-NLS-1$
				}

				return collator.compare(title1, title2);
			}
		});

		return configElements;
	}
}

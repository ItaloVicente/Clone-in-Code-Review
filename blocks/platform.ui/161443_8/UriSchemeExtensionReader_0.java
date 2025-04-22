package org.eclipse.urischeme;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.urischeme.internal.UriSchemeExtensionReader;

public class AutoRegisterSchemeHandlersJob extends Job {

	private static final String PROCESSED_SCHEMES_PREFERENCE = "processedSchemes"; //$NON-NLS-1$
	private static final String SCHEME_LIST_PREFERENCE_SEPARATOR = ","; //$NON-NLS-1$
	private static boolean alreadyTriggered = false;

	public AutoRegisterSchemeHandlersJob() {
		super(AutoRegisterSchemeHandlersJob.class.getSimpleName());
		setSystem(true);
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		IOperatingSystemRegistration osRegistration = IOperatingSystemRegistration.getInstance();
		IUriSchemeExtensionReader extensionReader = IUriSchemeExtensionReader.newInstance();
		IEclipsePreferences preferenceNode = InstanceScope.INSTANCE.getNode(UriSchemeExtensionReader.PLUGIN_ID);
		Collection<String> processedSchemes = new LinkedHashSet<>(Arrays
				.asList(preferenceNode.get(PROCESSED_SCHEMES_PREFERENCE, "").split(SCHEME_LIST_PREFERENCE_SEPARATOR))); //$NON-NLS-1$
		Collection<IScheme> toProcessSchemes = new LinkedHashSet<>(extensionReader.getSchemes());
		toProcessSchemes.removeIf(scheme -> processedSchemes.contains(scheme.getName()));
		if (toProcessSchemes.isEmpty()) {
			alreadyTriggered = true;
			return Status.OK_STATUS;
		}
		try {
			toProcessSchemes = osRegistration.getSchemesInformation(toProcessSchemes).stream() //
					.filter(scheme -> !scheme.isHandled()) //
					.collect(Collectors.toSet());
			if (toProcessSchemes.isEmpty()) {
				alreadyTriggered = true;
				return Status.OK_STATUS;
			}
			osRegistration.handleSchemes(toProcessSchemes, Collections.emptyList());
			processedSchemes.addAll(toProcessSchemes.stream().map(IScheme::getName).collect(Collectors.toList()));
			preferenceNode.put(PROCESSED_SCHEMES_PREFERENCE,
					processedSchemes.stream().collect(Collectors.joining(SCHEME_LIST_PREFERENCE_SEPARATOR)));
			preferenceNode.flush();
			alreadyTriggered = true;
			return Status.OK_STATUS;
		} catch (Exception e) {
			return new Status(IStatus.ERROR, UriSchemeExtensionReader.PLUGIN_ID, e.getMessage(), e);
		}
	}

	@Override
	public boolean shouldSchedule() {
		return !alreadyTriggered;
	}
}

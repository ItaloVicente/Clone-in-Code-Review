/*******************************************************************************
 * Copyright (c) 2020 Red Hat Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.urischeme;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.urischeme.internal.UriSchemeExtensionReader;

/**
 * Looks at all handlers, and register all handlers that were not attempted to
 * be registered earlier. This typically will try associating newly installed
 * handlers or handlers that were recently "freed" from other default
 * application.
 *
 * @since 1.1
 */
public class AutoRegisterSchemeHandlersJob extends Job {

	private static final String SCHEME_LIST_PREFERENCE_SEPARATOR = ",; //$NON-NLS-1$
-	private static boolean alreadyTriggered = false;
-	private IEclipsePreferences preferenceNode;
-	private IUriSchemeExtensionReader extensionReader;
-	private IOperatingSystemRegistration osRegistration;	
-
-	/**
-	 *
-	 */
-	public AutoRegisterSchemeHandlersJob() {
-		this(InstanceScope.INSTANCE.getNode(UriSchemeExtensionReader.PLUGIN_ID),
-				IUriSchemeExtensionReader.newInstance(), IOperatingSystemRegistration.getInstance());
-	}
-
-	AutoRegisterSchemeHandlersJob(IEclipsePreferences preferenceNode, IUriSchemeExtensionReader extensionReader,
-			IOperatingSystemRegistration osRegistration) {
-		super(AutoRegisterSchemeHandlersJob.class.getSimpleName());
-		this.preferenceNode = preferenceNode;
-		this.extensionReader = extensionReader;
-		this.osRegistration = osRegistration;
-		setSystem(true);
-	}
-
-	@Override
-	protected IStatus run(IProgressMonitor monitor) {
-		Collection<String> processedSchemes = new LinkedHashSet<>(Arrays
-				.asList(preferenceNode.get(PROCESSED_SCHEMES_PREFERENCE, ").split(SCHEME_LIST_PREFERENCE_SEPARATOR))); //$NON-NLS-1$
-		Collection<IScheme> toProcessSchemes = new LinkedHashSet<>(extensionReader.getSchemes());
-		toProcessSchemes.removeIf(scheme -> processedSchemes.contains(scheme.getName()));
-		if (toProcessSchemes.isEmpty()) {
-			alreadyTriggered = true;
-			return Status.OK_STATUS;
-		}
-		try {
-			toProcessSchemes = osRegistration.getSchemesInformation(toProcessSchemes).stream() //
-					.filter(scheme -> !scheme.schemeIsHandledByOther()) //
-					.collect(Collectors.toSet());
-			if (toProcessSchemes.isEmpty()) {
-				alreadyTriggered = true;
-				return Status.OK_STATUS;
-			}
-			osRegistration.handleSchemes(toProcessSchemes, Collections.emptyList());
-			processedSchemes.addAll(toProcessSchemes.stream().map(IScheme::getName).collect(Collectors.toList()));
-			preferenceNode.put(PROCESSED_SCHEMES_PREFERENCE,
-					processedSchemes.stream().collect(Collectors.joining(SCHEME_LIST_PREFERENCE_SEPARATOR)));
-			preferenceNode.flush();
-			alreadyTriggered = true;
-		} catch (Exception e) {
-			Platform.getLog(getClass()).error(e.getMessage(), e);
-		}
-		return Status.OK_STATUS;
-	}
-
-	@Override
-	public boolean shouldSchedule() {
-		return !(alreadyTriggered || Platform.getPreferencesService().getBoolean(UriSchemeExtensionReader.PLUGIN_ID,
-				SKIP_PREFERENCE, false, null));
-	}
-}
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IOperatingSystemRegistration.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IOperatingSystemRegistration.java
deleted file mode 100644
index 7f03bce3ae..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IOperatingSystemRegistration.java
+++ /dev/null
@@ -1,87 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2018 SAP SE and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     SAP SE - initial version
- *******************************************************************************/
-package org.eclipse.urischeme;
-
-import java.util.Collection;
-import java.util.List;
-
-import org.eclipse.core.runtime.Platform;
-import org.eclipse.urischeme.internal.registration.RegistrationLinux;
-import org.eclipse.urischeme.internal.registration.RegistrationMacOsX;
-import org.eclipse.urischeme.internal.registration.RegistrationWindows;
-
-/**
- * Interface for registration or uri schemes in the different operating systems
- * (macOS, Linux and Windows)<br>
- * Call <code>getInstance()</code> to get an OS specific instance.
- *
- */
-public interface IOperatingSystemRegistration {
-
-	/**
-	 * Returns the operating system specific implementation of
-	 * IOperatingSystemRegistration
-	 *
-	 * @return an instance of IOperatingSystemRegistration
-	 */
-	static IOperatingSystemRegistration getInstance() {
-		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
-			return new RegistrationMacOsX();
-		} else if (Platform.OS_LINUX.equals(Platform.getOS())) {
-			return new RegistrationLinux();
-		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
-			return new RegistrationWindows();
-		}
-		return null;
-	}
-
-	/**
-	 * Registers/Unregisters uri schemes for this Eclipse installation
-	 *
-	 * @param toAdd    the uri schemes which this Eclipse should handle additionally
-	 * @param toRemove the uri schemes which this Eclipse should not handle anymore
-	 * @throws Exception something went wrong
-	 */
-	void handleSchemes(Collection<IScheme> toAdd, Collection<IScheme> toRemove) throws Exception;
-
-	/**
-	 * Takes the given schemes and fills information like whether they are
-	 * registered for this instance and the handler location. <br>
-	 * <br>
-	 * <strong>Note:</strong> On macOS this is a long running operation any may need
-	 * multiple seconds to finish. <strong>So this should not be called in the UI
-	 * thread</strong>.
-	 *
-	 * @param schemes The schemes that should be checked for registrations.
-	 * @return schemes with information
-	 * @throws Exception something went wrong
-	 */
-	List<ISchemeInformation> getSchemesInformation(Collection<IScheme> schemes) throws Exception;
-
-	/**
-	 * @return the Eclipse executable
-	 */
-	String getEclipseLauncher();
-
-	/**
-	 *
-	 * This method returns if the current operating system allows to register an uri
-	 * scheme that this already handled by another application.
-	 *
-	 * If the operating system does store this information in de-central way the
-	 * implementation should return false.
-	 *
-	 * @return <code>true</code> if registering of other application's uri scheme is
-	 *         supported - <code>false</code> otherwise.
-	 */
-	boolean canOverwriteOtherApplicationsRegistration();
-
-}
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IScheme.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IScheme.java
deleted file mode 100644
index bed6fb9f1b..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IScheme.java
+++ /dev/null
@@ -1,28 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2018 SAP SE and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     SAP SE - initial version
- *******************************************************************************/
-package org.eclipse.urischeme;
-
-/**
- * The basic information of an URI scheme like name and description.
- *
- */
-public interface IScheme {
-
-	/**
-	 * @return the name of the scheme
-	 */
-	String getName();
-
-	/**
-	 * @return the description of the scheme
-	 */
-	String getDescription();
-}
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/ISchemeInformation.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/ISchemeInformation.java
deleted file mode 100644
index 353c313b80..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/ISchemeInformation.java
+++ /dev/null
@@ -1,40 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2018 SAP SE and others.
- * All rights reserved. This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License v1.0
- * which accompanies this distribution, and is available at
- * http://www.eclipse.org/legal/epl-v10.html
- *
- * Contributors:
- *     SAP SE - initial version
- *******************************************************************************/
-package org.eclipse.urischeme;
-
-/**
- * The basic information of an URI scheme with regards to the handler.
- *
- */
-public interface ISchemeInformation extends IScheme {
-
-	/**
-	 * @return true if the scheme is handled by the running Eclipse installation;
-	 *         false otherwise
-	 */
-	boolean isHandled();
-
-	/**
-	 * @return the path of the application
-	 */
-	String getHandlerInstanceLocation();
-
-	/**
-	 * @return true if the scheme is handled by another application; false
-	 *         otherwise.
-	 */
-	default boolean schemeIsHandledByOther() {
-		boolean schemeIsNotHandled = !isHandled();
-		String handlerInstanceLocation = getHandlerInstanceLocation();
-		boolean handlerLocationIsSet = handlerInstanceLocation != null && !handlerInstanceLocation.isEmpty();
-		return schemeIsNotHandled && handlerLocationIsSet;
-	}
-}
\ No newline at end of file
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeExtensionReader.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeExtensionReader.java
deleted file mode 100644
index 11043a76e3..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeExtensionReader.java
+++ /dev/null
@@ -1,49 +0,0 @@
-/*******************************************************************************
-* Copyright (c) 2018 SAP SE and others.
-* All rights reserved. This program and the accompanying materials
-* are made available under the terms of the Eclipse Public License v1.0
-* which accompanies this distribution, and is available at
-* http://www.eclipse.org/legal/epl-v10.html
-*
-* Contributors:
-*     SAP SE - initial API and implementation
-*******************************************************************************/
-package org.eclipse.urischeme;
-
-import java.util.Collection;
-
-import org.eclipse.core.runtime.CoreException;
-import org.eclipse.urischeme.internal.UriSchemeExtensionReader;
-
-/**
- * API for reading available URI schemes from the extension registry
- *
- */
-public interface IUriSchemeExtensionReader {
-
-	/**
-	 * @return an instance to read out URI scheme handlers as registered in
-	 *         extension point
-	 *         <code> org.eclipse.core.runtime.uriSchemeHandlers</code>
-	 */
-	static IUriSchemeExtensionReader newInstance() {
-		return new UriSchemeExtensionReader();
-	}
-
-	/**
-	 *
-	 * @return The list of available URI schemes
-	 */
-	Collection<IScheme> getSchemes();
-
-	/**
-	 * Creates the handler for a given URI scheme as registered in extension point
-	 * <code> org.eclipse.core.runtime.uriSchemeHandlers</code>
-	 *
-	 * @param uriScheme The URI scheme
-	 * @return The handler implementation for the given URI scheme
-	 * @throws CoreException if creation failed; implementation dependent
-	 */
-	IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException;
-
-}
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeHandler.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeHandler.java
deleted file mode 100644
index 39bae06dce..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeHandler.java
+++ /dev/null
@@ -1,29 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2018 SAP SE and others.
- *
- * This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License 2.0
- * which accompanies this distribution, and is available at
- * https://www.eclipse.org/legal/epl-2.0/
- *
- * SPDX-License-Identifier: EPL-2.0
- *
- * Contributors:
- *     SAP SE - initial version
- *******************************************************************************/
-package org.eclipse.urischeme;
-
-/**
- * This interface belongs to extension point
- * org.eclipse.core.runtime.uriSchemeHandlers.
- *
- */
-public interface IUriSchemeHandler {
-
-	/**
-	 * Sent by the platform when an URL needs to be handled.
-	 *
-	 * @param uri The URI to be handled
-	 */
-	void handle(String uri);
-}
\ No newline at end of file
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeProcessor.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeProcessor.java
deleted file mode 100644
index 7d8e6e238a..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/IUriSchemeProcessor.java
+++ /dev/null
@@ -1,60 +0,0 @@
-/*******************************************************************************
- * Copyright (c) 2018 SAP SE and others.
- *
- * This program and the accompanying materials
- * are made available under the terms of the Eclipse Public License 2.0
- * which accompanies this distribution, and is available at
- * https://www.eclipse.org/legal/epl-2.0/
- *
- * SPDX-License-Identifier: EPL-2.0
- *
- * Contributors:
- *     SAP SE - initial version
- *******************************************************************************/
-package org.eclipse.urischeme;
-
-import java.net.URI;
-
-import org.eclipse.core.runtime.CoreException;
-import org.eclipse.urischeme.internal.UriSchemeProcessor;
-
-/**
- * API to process URI scheme handling as defined in extension point
- * <code> org.eclipse.core.runtime.uriSchemeHandlers</code>
- *
- */
-public interface IUriSchemeProcessor {
-	/**
-	 * The singleton instance
-	 */
-	IUriSchemeProcessor INSTANCE = new UriSchemeProcessor();
-
-	/**
-	 * Handle an URI with the given uriScheme
-	 *
-	 * @param uriScheme the scheme of the URI
-	 * @param uri       the complete URI
-	 * @throws CoreException if URI handling failed; implementation dependent
-	 */
-	void handleUri(String uriScheme, String uri) throws CoreException;
-
-	/**
-	 * Handle an URI with the given uriScheme
-	 *
-	 * @param uri the complete URI
-	 * @throws CoreException if URI handling failed; implementation dependent
-	 * @since 1.1
-	 */
-	default void handleUri(URI uri) throws CoreException {
-		handleUri(uri.getScheme(), uri.toString());
-	}
-
-	/**
-	 * Return whether a handler can process the given URI, according to its scheme.
-	 *
-	 * @param uri
-	 * @return whether a handler can process the given URI, according to its scheme.
-	 * @since 1.1
-	 */
-	boolean canHandle(URI uri);
-}
\ No newline at end of file
diff --git a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/internal/UriSchemeExtensionReader.java b/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/internal/UriSchemeExtensionReader.java
deleted file mode 100644
index 901fd6740b..0000000000
--- a/bundles/org.eclipse.urischeme/src/org/eclipse/urischeme/internal/UriSchemeExtensionReader.java
+++ /dev/null
@@ -1,138 +0,0 @@
-/*******************************************************************************
-* Copyright (c) 2018 SAP SE and others.
-* All rights reserved. This program and the accompanying materials
-* are made available under the terms of the Eclipse Public License v1.0
-* which accompanies this distribution, and is available at
-* http://www.eclipse.org/legal/epl-v10.html
-*
-* Contributors:
-*     SAP SE - initial API and implementation
-*******************************************************************************/
-package org.eclipse.urischeme.internal;
-
-import java.util.ArrayList;
-import java.util.Collection;
-import java.util.Objects;
-
-import org.eclipse.core.runtime.CoreException;
-import org.eclipse.core.runtime.IConfigurationElement;
-import org.eclipse.core.runtime.IExtensionRegistry;
-import org.eclipse.core.runtime.IStatus;
-import org.eclipse.core.runtime.RegistryFactory;
-import org.eclipse.core.runtime.Status;
-import org.eclipse.urischeme.IScheme;
-import org.eclipse.urischeme.IUriSchemeExtensionReader;
-import org.eclipse.urischeme.IUriSchemeHandler;
-
-/**
- * Implementation of the API to read available URI schemes from the extension
- * registry as defined in extension point
- * <code> org.eclipse.core.runtime.uriSchemeHandlers</code>
- */
-public class UriSchemeExtensionReader implements IUriSchemeExtensionReader {
-
-	/**
-	 * The bundle symbolic name.
-	 */
-	public static final String PLUGIN_ID = org.eclipse.urischeme"; //$NON-NLS-1$
	/**
	 * Id of the extension point for uri scheme handlers
	 */
	/**
	 * Attribute "uriScheme" of an registered uri scheme handler
	 */
	/**
	 * Attribute "uriSchemeDecription" of an registered uri scheme handler
	 *
	 */
	/**
	 * Attribute "class" of an registered uri scheme handler
	 */

	IConfigurationElement[] configurationElements = null;

	@Override
	public Collection<IScheme> getSchemes() {
		IConfigurationElement[] elements = getOrReadConfigurationElements();
		Collection<IScheme> schemes = new ArrayList<>();
		for (IConfigurationElement element : elements) {
			String schemeName = element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME);
			String schemeDescription = element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME_DESCRIPTION);
			IScheme scheme = new Scheme(schemeName, schemeDescription);
			schemes.add(scheme);
		}
		return schemes;
	}


	@Override
	public IUriSchemeHandler getHandlerFromExtensionPoint(String uriScheme) throws CoreException {
		IConfigurationElement[] elements = getOrReadConfigurationElements();

		for (IConfigurationElement element : elements) {
			if (uriScheme.equals(element.getAttribute(EXT_POINT_ATTRIBUTE_URI_SCHEME))) {
				return createExecutableSchemeHandler(element);
			}
		}
		return null;
	}

	private IConfigurationElement[] getOrReadConfigurationElements() {
		if (this.configurationElements == null) {
			IExtensionRegistry registry = RegistryFactory.getRegistry();
			this.configurationElements = registry.getConfigurationElementsFor(EXT_POINT_ID_URI_SCHEME_HANDLERS);
		}
		return configurationElements;
	}

	private IUriSchemeHandler createExecutableSchemeHandler(IConfigurationElement element) throws CoreException {
		Object executableExtension = element.createExecutableExtension(EXT_POINT_ATTRIBUTE_CLASS);
		if (executableExtension instanceof IUriSchemeHandler) {
			return (IUriSchemeHandler) executableExtension;
		}
		throw new CoreException(new Status(IStatus.ERROR, PLUGIN_ID,
	}

	private static class Scheme implements IScheme {

		private String uriScheme;
		private String uriSchemeDescription;

		public Scheme(String uriScheme, String uriSchemeDescription) {
			super();
			this.uriScheme = uriScheme;
			this.uriSchemeDescription = uriSchemeDescription;
		}

		@Override
		public String getName() {
			return uriScheme;
		}

		@Override
		public String getDescription() {
			return uriSchemeDescription;
		}

		@Override
		public boolean equals(Object o) {
			if (o.getClass() != this.getClass()) {
				return false;
			}
			Scheme other = (Scheme) o;
			return Objects.equals(this.uriScheme, other.uriScheme)
					&& Objects.equals(this.uriSchemeDescription, other.uriSchemeDescription);
		}

		@Override
		public int hashCode() {
			return Objects.hash(uriScheme, uriSchemeDescription);
		}
	}

}

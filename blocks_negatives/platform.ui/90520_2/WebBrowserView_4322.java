/*******************************************************************************
 * Copyright (c) 2003, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - Initial API and implementation
 *     Martin Oberhuber (Wind River) - [292882] Default Browser on Solaris
 *     Tomasz Zarna (Tasktop Technologies) - [429546] External Browser with parameters
 *******************************************************************************/
package org.eclipse.ui.internal.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.Util;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
/**
 * Utility class for the Web browser tools.
 */
public class WebBrowserUtil {

	public static Boolean isInternalBrowserOperational;

	private static final char STYLE_SEP = '-';

	private static final int DEFAULT_STYLE = BrowserViewer.BUTTON_BAR
			| BrowserViewer.LOCATION_BAR;

	/**
	 * WebBrowserUtil constructor comment.
	 */
	public WebBrowserUtil() {
		super();
	}

	/**
	 * Returns true if we're running on Windows.
	 *
	 * @return boolean
	 */
	public static boolean isWindows() {
			return true;
		return false;
	}

	/**
	 * Returns true if we're running on linux.
	 *
	 * @return boolean
	 */
	public static boolean isLinux() {
			return true;
		return false;
	}

	/**
	 * Open a dialog window.
	 *
	 * @param message
	 *            java.lang.String
	 */
	public static void openError(final String message) {
		Display d = Display.getCurrent();
		if (d == null)
			d = Display.getDefault();
		d.asyncExec(() -> MessageDialog.openError(null, Messages.errorDialogTitle, message));
	}

	/**
	 * Open a dialog window.
	 *
	 * @param message
	 *            java.lang.String
	 */
	public static void openMessage(final String message) {
		Display d = Display.getCurrent();
		if (d == null)
			d = Display.getDefault();

		d.asyncExec(() -> MessageDialog.openInformation(null, Messages.searchingTaskName, message));
	}

	/**
	 * Returns whether it should be possible to use the internal browser or not,
	 * based on whether or not the org.eclipse.swt.Browser class can be
	 * found/loaded. If it can it means is is supported on the platform in which
	 * this plugin is running. If not, disable the ability to use the internal
	 * browser. This method checks to see if it can new up a new
	 * ExternalBrowserInstance. If the SWT widget can not be bound to the
	 * particular operating system it throws an SWTException. We catch that and
	 * set a boolean flag which represents whether or not we were successfully
	 * able to create a ExternalBrowserInstance instance or not. If not, don't
	 * bother adding the Internal Web ExternalBrowserInstance that uses this
	 * widget. Designed to be attemped only once and the flag set used
	 * throughout.
	 *
	 * @return boolean
	 */
	public static boolean canUseInternalWebBrowser() {
		if (isInternalBrowserOperational != null)
			return isInternalBrowserOperational.booleanValue();

		try {
			Class.forName(BROWSER_PACKAGE_NAME);
		} catch (ClassNotFoundException e) {
			isInternalBrowserOperational = Boolean.FALSE;
			return false;
		}

		Shell shell = null;
		try {
			shell = new Shell(PlatformUI.getWorkbench().getDisplay());
			new Browser(shell, SWT.NONE);
			isInternalBrowserOperational = Boolean.TRUE;
			return true;
		} catch (Throwable t) {
			WebBrowserUIPlugin.getInstance().getLog().log(
					new Status(IStatus.WARNING, WebBrowserUIPlugin.PLUGIN_ID,
							0, message.toString() , null));
			isInternalBrowserOperational = Boolean.FALSE;
			return false;
		} finally {
			if (shell != null)
				shell.dispose();
		}
	}

	public static boolean canUseSystemBrowser() {
	}

	public static List<String> getExternalBrowserPaths() {
		List<String> paths = new ArrayList<>();
		Iterator<IBrowserDescriptor> iterator = BrowserManager.getInstance()
				.getWebBrowsers().iterator();
		while (iterator.hasNext()) {
			IBrowserDescriptor wb = iterator.next();
			if (wb != null && wb.getLocation() != null)
				paths.add(wb.getLocation().toLowerCase());
		}
		return paths;
	}

	/**
	 * Add any supported EXTERNAL web browsers found after an arbitrary check in
	 * specific paths
	 */
	public static void addFoundBrowsers(List<IBrowserDescriptor> browsers2) {
		List<String> paths = getExternalBrowserPaths();

		String os = Platform.getOS();
		File[] roots = getUsableDrives(File.listRoots());

		IBrowserExt[] browsers = WebBrowserUIPlugin.getBrowsers();
		int size = browsers.length;
		for (int i = 0; i < size; i++) {
			IBrowserExt browserExt = browsers[i];
			String[] locations = browserExt.getDefaultLocations();
			if (locations != null
					&& browserExt.getOS().toLowerCase().indexOf(os) >= 0) {
				int size2 = locations.length;
				for (int j = 0; j < size2; j++) {
					String location = locations[j];

					String foundBrowserPath = locateBrowser(paths, location, roots);

					if (foundBrowserPath != null) {
						BrowserDescriptor descriptor = new BrowserDescriptor();
						descriptor.name = browserExt.getName();
						descriptor.location = foundBrowserPath;
						descriptor.parameters = browserExt
								.getParameters();
						browsers2.add(descriptor);
						j += size2;
					}

				}
			}
		}
	}

	/*
	 * Look for the file on each of the search roots.
	 * If the location starts with a Windows environment variable, expand it.
	 */
	private static String locateBrowser(List<String> alreadyFoundPaths,
			String location, File[] searchRoots) {
		int rootSize = searchRoots.length;

			int envVarEnd = location.indexOf('%', 1);
			if (envVarEnd != -1) {
				try {
					String expanded = System.getenv(location.substring(1, envVarEnd));
					if (expanded != null) {
						File f = new File(expanded + location.substring(envVarEnd + 1));
						String absolutePath = f.getAbsolutePath();
						if (!alreadyFoundPaths.contains(absolutePath.toLowerCase())) {
							if (f.exists()) {
								return absolutePath;
							}
						}
						return null;
					}
				} catch (Exception e) {
				}
			}
		}

		for (int k = 0; k < rootSize; k++) {
			try {
				File f = new File(searchRoots[k], location);
				String absolutePath = f.getAbsolutePath();
				if (!alreadyFoundPaths.contains(absolutePath
						.toLowerCase())) {
					if (f.exists()) {
						return absolutePath;
					}
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	private static File[] getUsableDrives(File[] roots) {
		if (!Platform.getOS().equals(Platform.OS_WIN32))
			return roots;
		ArrayList<File> list = new ArrayList<>();
		for (File root : roots) {
			String path = root.getAbsolutePath();
			if (path != null
				continue;
			list.add(root);
		}
		return list.toArray(new File[list.size()]);
	}

	/**
	 * Create an external Web browser if the file matches the default (known)
	 * browsers.
	 *
	 * @param file
	 * @return an external browser working copy
	 */
	public static IBrowserDescriptorWorkingCopy createExternalBrowser(File file) {
		if (file == null || !file.isFile())
			return null;

		String executable = file.getName();
		IBrowserExt[] browsers = WebBrowserUIPlugin.getBrowsers();
		int size = browsers.length;
		for (int i = 0; i < size; i++) {
			if (executable.equals(browsers[i].getExecutable())) {
				IBrowserDescriptorWorkingCopy browser = BrowserManager
						.getInstance().createExternalWebBrowser();
				browser.setName(browsers[i].getName());
				browser.setLocation(file.getAbsolutePath());
				browser.setParameters(browsers[i].getParameters());
				return browser;
			}
		}

		return null;
	}

	/**
	 * Encodes browser style in the secondary id as id-style
	 *
	 * @param browserId
	 * @param style
	 * @return secondaryId
	 */
	public static String encodeStyle(String browserId, int style) {
		return browserId + STYLE_SEP + style;
	}

	/**
	 * Decodes secondary id into a browser style.
	 *
	 * @param secondaryId
	 * @return style
	 */
	public static int decodeStyle(String secondaryId) {
		if (secondaryId != null) {
			int sep = secondaryId.lastIndexOf(STYLE_SEP);
			if (sep != -1) {
				String stoken = secondaryId.substring(sep + 1);
				try {
					return Integer.parseInt(stoken);
				} catch (NumberFormatException e) {
				}
			}
		}
		return DEFAULT_STYLE;
	}

	public static String decodeId(String encodedId) {
		int sep = encodedId.lastIndexOf(STYLE_SEP);
		if (sep != -1) {
			return encodedId.substring(0, sep);
		}
		return encodedId;
	}

	/**
	 * @deprecated Please use {@link #createParameterArray(String, String)}
	 *             instead.
	 */
	@Deprecated
	public static String createParameterString(String parameters, String urlText) {
		String params = parameters;
		String url = urlText;
		if (url == null) {
		}
		if (params == null)

		int urlIndex = params.indexOf(IBrowserDescriptor.URL_PARAMETER);
		if (urlIndex >= 0) {
			params = params.substring(0, urlIndex) + url
					+ params.substring(urlIndex + IBrowserDescriptor.URL_PARAMETER.length());
		} else {
			params += url;
		}
		return params;
	}

	public static String[] createParameterArray(String parameters, String urlText) {
		return tokenize(createParameterString(parameters, urlText));
	}

	private static String[] tokenize(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string);
		String[] tokens = new String[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++)
			tokens[i] = tokenizer.nextToken();
		return tokens;
	}
}

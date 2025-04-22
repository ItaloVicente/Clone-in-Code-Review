package org.eclipse.e4.ui.internal.workbench.swt.dialog.about.ui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.ui.internal.workbench.swt.dialog.about.DialogPlugin;
import org.eclipse.e4.ui.workbench.swt.internal.copy.WorkbenchSWTMessages;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.FrameworkUtil;


public class AboutUtils {

	private final static String ERROR_LOG_COPY_FILENAME = "log"; //$NON-NLS-1$

	public static AboutItem scan(String s) {
		List<Object> linkRanges = new ArrayList<Object>();
		List<Object> links = new ArrayList<Object>();


		int urlSeparatorOffset = s.indexOf("://"); //$NON-NLS-1$
		while (urlSeparatorOffset >= 0) {

			boolean startDoubleQuote = false;

			int urlOffset = urlSeparatorOffset;
			char ch;
			do {
				urlOffset--;
				ch = ' ';
				if (urlOffset > -1)
					ch = s.charAt(urlOffset);
				startDoubleQuote = ch == '"';
			} while (Character.isUnicodeIdentifierStart(ch));
			urlOffset++;

			StringTokenizer tokenizer = new StringTokenizer(s.substring(urlSeparatorOffset + 3), " \t\n\r\f<>", false); //$NON-NLS-1$
			if (!tokenizer.hasMoreTokens())
				return null;

			int urlLength = tokenizer.nextToken().length() + 3 + urlSeparatorOffset - urlOffset;

			if (startDoubleQuote) {
				int endOffset = -1;
				int nextDoubleQuote = s.indexOf('"', urlOffset);
				int nextWhitespace = s.indexOf(' ', urlOffset);
				if (nextDoubleQuote != -1 && nextWhitespace != -1)
					endOffset = Math.min(nextDoubleQuote, nextWhitespace);
				else if (nextDoubleQuote != -1)
					endOffset = nextDoubleQuote;
				else if (nextWhitespace != -1)
					endOffset = nextWhitespace;
				if (endOffset != -1)
					urlLength = endOffset - urlOffset;
			}

			linkRanges.add(new int[] { urlOffset, urlLength });
			links.add(s.substring(urlOffset, urlOffset + urlLength));

			urlSeparatorOffset = s.indexOf("://", urlOffset + urlLength + 1); //$NON-NLS-1$
		}
		return new AboutItem(s, (int[][]) linkRanges.toArray(new int[linkRanges.size()][2]),
				(String[]) links.toArray(new String[links.size()]));
	}

	public static boolean openBrowser(Shell shell, URL url) {
		if (url != null) {
			try {
				url = Platform.asLocalURL(url);
			} catch (IOException e) {
				return false;
			}
		}
		if (url == null) {
			return false;
		}
		openLink(shell, url.toString());
		return true;
	}

	public static void openLink(Shell shell, String href) {
		if (href.startsWith("file:")) { //$NON-NLS-1$
			href = href.substring(5);
			while (href.startsWith("/")) { //$NON-NLS-1$
				href = href.substring(1);
			}
			href = "file:///" + href; //$NON-NLS-1$
		}
		try {
			Desktop.getDesktop().browse(new URL(urlEncodeForSpaces(href.toCharArray())).toURI());
		} catch (IOException ioe) {
			openWebBrowserError(shell, href, ioe);
		} catch (URISyntaxException urie) {
			openWebBrowserError(shell, href, urie);
		}

	}

	private static String urlEncodeForSpaces(char[] input) {
		StringBuffer retu = new StringBuffer(input.length);
		for (int i = 0; i < input.length; i++) {
			if (input[i] == ' ') {
				retu.append("%20"); //$NON-NLS-1$
			} else {
				retu.append(input[i]);
			}
		}
		return retu.toString();
	}

	public static String[] getArrayFromList(String prop, String separator) {
		if (prop == null || prop.trim().equals("")) { //$NON-NLS-1$
			return new String[0];
		}
		List<String> list = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(prop, separator);
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken().trim();
			if (!token.equals("")) { //$NON-NLS-1$
				list.add(token);
			}
		}
		return list.isEmpty() ? new String[0] : (String[]) list.toArray(new String[list.size()]);
	}

	public static void handleStatus(String status) {
		handleStatus(status, IStatus.ERROR, null);
	}

	public static void handleStatus(String status, Exception e) {
		handleStatus(status, IStatus.ERROR, e);
	}

	public static void handleStatus(String status, int level) {
		handleStatus(status, level, null);
	}

	public static void handleStatus(String status, int level, Exception e) {
		ILog log = Platform.getLog(FrameworkUtil.getBundle(AboutUtils.class));
		log.log(new Status(level, DialogPlugin.ID, status, e));
	}

	private static void openWebBrowserError(Shell shell, final String href, final Throwable t) {
		String title = WorkbenchSWTMessages.ProductInfoDialog_errorTitle;
		String msg = NLS.bind(WorkbenchSWTMessages.ProductInfoDialog_unableToOpenWebBrowser, href);

		AboutUtils.handleStatus(title + ": " + msg);
	}

	public static void openErrorLogBrowser(Shell shell) {
		String filename = Platform.getLogFileLocation().toOSString();

		File log = new File(filename);
		if (log.exists()) {
			File logCopy = makeDisplayCopy(log);
			if (logCopy != null) {
				AboutUtils.openLink(shell, "file:///" + logCopy.getAbsolutePath()); //$NON-NLS-1$
				return;
			}
			AboutUtils.openLink(shell, "file:///" + filename); //$NON-NLS-1$
			return;
		}
		MessageDialog.openInformation(shell, WorkbenchSWTMessages.AboutSystemDialog_noLogTitle,
				NLS.bind(WorkbenchSWTMessages.AboutSystemDialog_noLogMessage, filename));
	}

	private static File makeDisplayCopy(File file) {

		IPath path = Platform.getStateLocation(FrameworkUtil.getBundle(AboutUtils.class));


		if (path == null) {
			return null;
		}
		path = path.append(ERROR_LOG_COPY_FILENAME);
		File copy = path.toFile();
		FileReader in = null;
		FileWriter out = null;
		try {
			in = new FileReader(file);
			out = new FileWriter(copy);
			char buffer[] = new char[4096];
			int count;
			while ((count = in.read(buffer, 0, buffer.length)) > 0) {
				out.write(buffer, 0, count);
			}
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				return null;
			}
		}
		return copy;

	}

}

package org.eclipse.ui.internal.about;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.statushandlers.StatusManager;


public class AboutUtils {

	private final static String ERROR_LOG_COPY_FILENAME = "log"; //$NON-NLS-1$

	public static AboutItem scan(String s) {
		ArrayList linkRanges = new ArrayList();
		ArrayList links = new ArrayList();


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

			StringTokenizer tokenizer = new StringTokenizer(s
					.substring(urlSeparatorOffset + 3), " \t\n\r\f<>", false); //$NON-NLS-1$
			if (!tokenizer.hasMoreTokens())
				return null;

			int urlLength = tokenizer.nextToken().length() + 3
					+ urlSeparatorOffset - urlOffset;

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
		return new AboutItem(s, (int[][]) linkRanges.toArray(new int[linkRanges
				.size()][2]), (String[]) links
				.toArray(new String[links.size()]));
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
		IWorkbenchBrowserSupport support = PlatformUI.getWorkbench()
				.getBrowserSupport();
		try {
			IWebBrowser browser = support.getExternalBrowser();
			browser.openURL(new URL(urlEncodeForSpaces(href.toCharArray())));
		} catch (MalformedURLException e) {
			openWebBrowserError(shell, href, e);
		} catch (PartInitException e) {
			openWebBrowserError(shell, href, e);
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

	private static void openWebBrowserError(Shell shell, final String href,
			final Throwable t) {
		String title = WorkbenchMessages.ProductInfoDialog_errorTitle;
		String msg = NLS.bind(
				WorkbenchMessages.ProductInfoDialog_unableToOpenWebBrowser,
				href);
		IStatus status = WorkbenchPlugin.getStatus(t);
		StatusUtil.handleStatus(status, title + ": " + msg, StatusManager.SHOW, //$NON-NLS-1$
				shell);
	}

	public static void openErrorLogBrowser(Shell shell) {
		String filename = Platform.getLogFileLocation().toOSString();

		File log = new File(filename);
		if (log.exists()) {
			File logCopy = makeDisplayCopy(log);
			if (logCopy != null) {
				AboutUtils.openLink(shell,
						"file:///" + logCopy.getAbsolutePath()); //$NON-NLS-1$
				return;
			}
			AboutUtils.openLink(shell, "file:///" + filename); //$NON-NLS-1$
			return;
		}
		MessageDialog.openInformation(shell,
				WorkbenchMessages.AboutSystemDialog_noLogTitle, NLS.bind(
						WorkbenchMessages.AboutSystemDialog_noLogMessage,
						filename));
	}

	private static File makeDisplayCopy(File file) {
		IPath path = WorkbenchPlugin.getDefault().getDataLocation();
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

package org.eclipse.egit.core.internal;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.Activator;
import org.eclipse.egit.core.CoreText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;
import org.eclipse.team.core.ProjectSetCapability;

public class GitURI {
	private static final String SCHEME_GIT = "git"; //$NON-NLS-1$

	private static final String KEY_PATH = "path"; //$NON-NLS-1$

	private static final String KEY_PROJECT = "project"; //$NON-NLS-1$

	private static final String KEY_TAG = "tag"; //$NON-NLS-1$

	private final URIish repository;

	private IPath path;

	private String tag;

	private String projectName;

	public GitURI(URI uri) {
		try {
			if (ProjectSetCapability.SCHEME_SCM.equals(uri.getScheme())) {
				final String ssp = uri.getSchemeSpecificPart();
				if (ssp.startsWith(SCHEME_GIT)) {
					int indexOfSemicolon = ssp.indexOf(';');
					URIish r = new URIish(ssp.substring(
							SCHEME_GIT.length() + 1, indexOfSemicolon));
					IPath p = null;
					String t = Constants.MASTER; // default
					String pn = null;
					String[] params = ssp.substring(indexOfSemicolon)
							.split(";"); //$NON-NLS-1$
					for (String param : params) {
						if (param.startsWith(KEY_PATH + '=')) {
							p = new Path(
									param.substring(param.indexOf('=') + 1));
						} else if (param.startsWith(KEY_TAG + '=')) {
							t = param.substring(param.indexOf('=') + 1);
						} else if (param.startsWith(KEY_PROJECT + '=')) {
							pn = param.substring(param.indexOf('=') + 1);
						}
					}
					this.repository = r;
					this.path = p;
					this.tag = t;
					this.projectName = pn;
					return;
				}
			}
			throw new IllegalArgumentException(NLS.bind(
					CoreText.GitURI_InvalidSCMURL,
					new String[] { uri.toString() }));
		} catch (URISyntaxException e) {
			Activator.logError(e.getMessage(), e);
			throw new IllegalArgumentException(NLS.bind(
					CoreText.GitURI_InvalidURI, new String[] { uri.toString(),
							e.getMessage() }));
		}
	}

	public IPath getPath() {
		return path;
	}

	public URIish getRepository() {
		return repository;
	}

	public String getTag() {
		return tag;
	}

	public String getProjectName() {
		return projectName;
	}
}

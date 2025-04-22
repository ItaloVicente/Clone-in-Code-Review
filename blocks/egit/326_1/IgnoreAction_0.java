package org.eclipse.egit.ui.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.team.core.Team;

public class IgnoredResources {


	public static boolean isIgnored(IResource resource) {
		return (Team.isIgnoredHint(resource) || checkGitIgnore(resource) || checkGitExclude(resource)) ;
	}

	public static boolean isGitIgnored(IResource resource) {
		return (Team.isIgnoredHint(resource) || checkGitIgnore(resource));
	}

	private static boolean checkGitIgnore(IResource resource) {
		IContainer container = resource.getParent();
		IFile gitignore = container.getFile(new Path(Constants.GITIGNORE_FILENAME));

		return searchForExact("/" + resource.getName(), gitignore); //$NON-NLS-1$
	}

	private static boolean checkGitExclude(IResource resource) {
		IProject project = resource.getProject();
		IFile exclude = project.getFile(".git/info/exclude"); //$NON-NLS-1$

		return searchForExact("/" + resource.getName(), exclude); //$NON-NLS-1$
	}

	private static boolean searchForExact(String target, final IFile file) {

			File f = new File(file.getLocation().toOSString());
			try {
				final BufferedReader br = new BufferedReader(new FileReader(f));
				try	 {
					String patt;
					while ((patt = br.readLine()) != null) {
						if (patt.length() < 1 | patt.charAt(0) == '#')
							continue;

						if (patt.equals(target))
							return true;
						Pattern p = Pattern.compile(patt.replace(".", "\\."). //$NON-NLS-1$ //$NON-NLS-2$
								replace("*", ".*").replace("?", ".")); //$NON-NLS-1$ //$NON-NLS-2$  //$NON-NLS-3$ //$NON-NLS-4$
						if (p.matcher(target).matches()) {
							return true;
						}
					}
					br.close();
				} catch (IOException e) {
					return false;
				} finally {
					try {
						br.close();
					} catch (IOException e) {
						return false;
					}
				}
			} catch (FileNotFoundException e) {
				return false;
			}
		return false;
	}
}

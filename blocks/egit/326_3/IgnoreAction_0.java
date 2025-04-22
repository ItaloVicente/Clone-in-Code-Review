package org.eclipse.egit.ui.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.team.core.Team;

public class IgnoredResources {

	private static class IgnoredStatus {
		private boolean value;
		public boolean changed;
		private boolean locked;

		IgnoredStatus (boolean value) {
			this.value = value;
			changed = false;
			locked = false;
		}

		public void setValue(boolean value) {
			if (!locked)
				this.value = value;
			changed = true;
		}

		public void lock() {
			locked = true;
		}

		public void unlock() {
			locked = false;
		}
	}


	public static boolean isGitIgnored(IResource resource) {
		return (Team.isIgnoredHint(resource) || checkGitIgnore(resource));
	}



	private static boolean checkGitIgnore(IResource resource) {
		IResource res = resource;
		boolean isContainer = false;
		IgnoredStatus retval = new IgnoredStatus(false);
		String name = ""; //$NON-NLS-1$

		while (!retval.changed && res.getProjectRelativePath() != Path.EMPTY) {
			name = "/" + res.getName() + name; //$NON-NLS-1$

			res = res.getParent();
			IFile gitignore = ((IContainer) res).getFile(
					new Path(Constants.GITIGNORE_FILENAME));
			if (!gitignore.exists())
				continue;

			if (res instanceof IContainer)
				isContainer = true;
			retval = searchForExact(name, gitignore, isContainer);
		}
		return retval.value;
	}



	private static IgnoredStatus searchForExact(String target, final IFile file, boolean isContainer) {
		IgnoredStatus retval = new IgnoredStatus(false);

		File f = new File(file.getLocation().toOSString());
		try {
			final BufferedReader br = new BufferedReader(new FileReader(f));
			try	 {
				String patt;
				while ((patt = br.readLine()) != null) {
					retval.unlock();
					String tar = target;

					if (patt.length() < 1 | patt.charAt(0) == '#')
						continue;


					if (patt.endsWith("/")) { //$NON-NLS-1$
						if (!isContainer) {
							continue;
						} else {
							patt = patt.substring(0, patt.length() - 1);
						}
					}


					if (retval.value) {
						if (!patt.startsWith("!")) {//$NON-NLS-1$
							continue;
						}
						else {
							patt = patt.substring(1);
						}
					} else {
						if (patt.startsWith("!")) {//$NON-NLS-1$
							retval.lock();
						}
					}


					if (!patt.contains("/")) {//$NON-NLS-1$
						tar = tar.substring(tar.lastIndexOf("/"), tar.length()); //$NON-NLS-1$
					} else if (patt.equals(tar)) {
						retval.setValue(retval.value ? false : true);
						continue;
					}


					if (!patt.startsWith("/")) { //$NON-NLS-1$
						patt = "/" + patt; //$NON-NLS-1$
					}


					String temppat = patt.replace(".", "\\."). //$NON-NLS-1$ //$NON-NLS-2$
					replace("*", "[^/]*").replace("?", "[^/]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

					Pattern p = Pattern.compile(temppat);
					if (p.matcher(tar).matches()) {
						retval.setValue(retval.value ? false : true);
					}
				}
				br.close();
				return retval;
			} catch (IOException e) {
				return retval;
			} finally {
				try {
					br.close();
				} catch (IOException e) {
					return retval;
				}
			}
		} catch (FileNotFoundException e) {
			return retval;
		}
	}
}

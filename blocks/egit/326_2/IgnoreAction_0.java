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
	private static class DoubleBool {
		private boolean value;
		public boolean changed;

		DoubleBool (boolean value) {
			this.value = value;
			changed = false;
		}

		public void setValue(boolean value) {
			this.value = value;
			changed = true;
		}
	}


	public static boolean isGitIgnored(IResource resource) {
		return (Team.isIgnoredHint(resource) || checkGitIgnore(resource));
	}



	private static boolean checkGitIgnore(IResource resource) {
		IResource res = resource;
		boolean isContainer = false;
		DoubleBool retval = new DoubleBool(false);
		String name = "/" + resource.getName(); //$NON-NLS-1$

		while (!retval.changed && res.getProjectRelativePath() != Path.EMPTY) {
			res = res.getParent();
			System.out.println("Checking file in " + res.getName());
			IFile gitignore = ((IContainer) res).getFile(
					new Path(Constants.GITIGNORE_FILENAME));

			if (res instanceof IContainer)
				isContainer = true;

			if (!gitignore.exists())
				continue;
			retval = searchForExact(name, gitignore, isContainer);
			name = "/" + res.getName() + name; //$NON-NLS-1$
		}
		return retval.value;
	}



	private static DoubleBool searchForExact(String target, final IFile file, boolean isContainer) {
		DoubleBool retval = new DoubleBool(false);

		File f = new File(file.getLocation().toOSString());
		try {
			final BufferedReader br = new BufferedReader(new FileReader(f));
			try	 {
				String patt;
				while ((patt = br.readLine()) != null) {
					String tar = target;

					System.out.println("*********");
					System.out.println("Parsing: " + patt);
					if (patt.length() < 1 | patt.charAt(0) == '#')
						continue;


					if (patt.endsWith("/") && !isContainer) { //$NON-NLS-1$
						System.out.println("Ends with '/' and " + target + " is not a container, skipping");
						continue;
					}


					if (retval.value) {
						if (!patt.startsWith("!")) {//$NON-NLS-1$
							System.out.println(patt + " is not a negation but a match has already been found, skipping");
							continue;
						}
						else {
							patt = patt.substring(1);
							System.out.println("Truncating to " + patt + " and seeking negations");
						}
					} else {
						if (patt.startsWith("!")) {//$NON-NLS-1$
							System.out.println(patt + " is a negation but no matches have been found. Skipping");
							continue;
						}
					}


					if (!patt.contains("/")) {//$NON-NLS-1$
						System.out.println("Pattern does not contain a /, treating as glob against filename");
						tar = tar.substring(tar.lastIndexOf("/"), tar.length());
					}



					if (patt.equals(tar)) {
						System.out.println("Direct match: " + patt + ", " + tar);
						retval.setValue(retval.value ? false : true);
						continue;
					}


					if (!patt.startsWith("/")) { //$NON-NLS-1$
						System.out.println(patt + " does not start with /, changing to /" + patt );
						patt = "/" + patt; //$NON-NLS-1$
					}


					String temppat = patt.replace(".", "\\."). //$NON-NLS-1$ //$NON-NLS-2$
					replace("*", "[^/]*").replace("?", "[^/]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					Pattern p = Pattern.compile(temppat);
					System.out.println("Changing to regular expression " + temppat);
					if (p.matcher(tar).matches()) {
						System.out.println("   - matched " + tar + " to regex" );
						retval.setValue(retval.value ? false : true);
					} else
						System.out.println("   - No match for " + tar);
				}
				br.close();
				return retval;
			} catch (IOException e) {
				return retval;
			} finally {
				System.out.println("\n\n\n");
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

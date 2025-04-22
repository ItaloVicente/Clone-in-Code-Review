/*******************************************************************************
 * Copyright (c) 2010 AGETO Service GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation of CVS factory
 *     Gunnar Wagenknecht - initial API and implementation
 *******************************************************************************/
package org.eclipse.egit.fetchfactory.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;
import org.eclipse.pde.build.Constants;
import org.eclipse.pde.build.IAntScript;
import org.eclipse.pde.build.IFetchFactory;
import org.eclipse.pde.internal.build.IPDEBuildConstants;
import org.eclipse.pde.internal.build.Utils;

/**
 * An <code>FetchTaskFactory</code> for building fetch scripts that will fetch
 * content from a Git repository (id: <code>GIT</code>).
 * <p>
 * Map file format: <code><pre>
 * 	type@id,[version]=GIT,args
 * </pre></code> <code>args</code> is a comma-separated list of key/value pairs
 * Accepted args include:
 * <ul>
 * <li><code>tag</code> - mandatory Git tag</li>
 * <li><code>repo</code> - mandatory repo location</li>
 * <li><code>path</code> - optional path relative to <code>repo</code> which
 * points to the element (otherwise it's assumed that the element is at the repo
 * root)</li>
 * <li><code>prebuilt</code> - optional boolean value indicating that the path
 * points to a pre-built bundle in the repository</li>
 * </ul>
 * </p>
 * <p>
 * Fetching is implemented as a three-step process.
 * <ol>
 * <li>The repository is cloned to local disc. If it already exists, it is
 * assumed that it was previously cloned and just new commits will be fetched.</li>
 * <li>The specified tag will be checked out in the local clone.</li>
 * <li>The content of the path will be copied to the final build location.</li>
 * </ol>
 * </p>
 */
@SuppressWarnings("restriction")
public class GITFetchTaskFactory implements IFetchFactory {


	public static final String OVERRIDE_TAG = ID;






	private static final String SEPARATOR = ",; //$NON-NLS-1$
-
-	// Git specific keys used in the map being passed around.
-	private static final String KEY_REPO = repo"; //$NON-NLS-1$












	private static void printArg(IAntScript script, String value) {
		final Map<String, String> params = new HashMap<String, String>(1);
		params.put("value", value); //$NON-NLS-1$
		script.printElement("arg", params); //$NON-NLS-1$
	}

	private void addProjectReference(Map<String, String> entryInfos) {
		final String repoLocation = entryInfos.get(KEY_REPO);
		final String path = entryInfos.get(KEY_PATH);
		final String projectName = entryInfos.get(KEY_ELEMENT_NAME);
		final String tag = entryInfos.get(IFetchFactory.KEY_ELEMENT_TAG);

		if (repoLocation != null && projectName != null) {
			final String sourceUrl = asReference(repoLocation, path,
					projectName, tag);
			if (sourceUrl != null) {
				entryInfos.put(Constants.KEY_SOURCE_REFERENCES, sourceUrl);
			}
		}
	}

	@Override
	public void addTargets(IAntScript script) {
		final Map<String, String> params = new HashMap<String, String>(3);
		final List<String> args = new ArrayList<String>(5);


		script.printTargetDeclaration(
				"GitCheckSkipClone", null, null, null, null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		printConditionStart(script, "skipClone", null, null); //$NON-NLS-1$
		script.incrementIdent();
		printAvailableFile(script,
				Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH));
		printAvailableFile(script, Utils.getPropertyFormat(PROP_FILETOCHECK));
		script.decrementIdent();
		printConditionEnd(script);
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_CLONE_REPO,
				"GitCheckSkipClone", null, "skipClone", null); //$NON-NLS-1$ //$NON-NLS-2$
		printGitRepoBaseLocationDefault(script);
		params.put("dir", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH)); //$NON-NLS-1$
		script.printElement("mkdir", params); //$NON-NLS-1$
		args.add(Utils.getPropertyFormat(PROP_GITREPO));
		printGitTask(script, "clone", args); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_UPDATE_REPO, null,
				Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH),
				"${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		args.clear();
		printGitTask(script, "fetch", null); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_CHECKOUT_TAG, null,
				Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH),
				"${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		args.clear();
		args.add(Utils.getPropertyFormat(PROP_TAG));
		printGitTask(script, "checkout", args); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_GET_ELEMENT_FROM_REPO, null,
				Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH),
				"${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		params.clear();
		params.put("todir", Utils.getPropertyFormat(PROP_DESTINATIONFOLDER)); //$NON-NLS-1$
		script.printStartTag("copy", params); //$NON-NLS-1$
		script.incrementIdent();
		params.clear();
		params.put(
				"dir", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH) + "/" + Utils.getPropertyFormat(PROP_PATH)); //$NON-NLS-1$ //$NON-NLS-2$
		script.printElement("fileset", params); //$NON-NLS-1$
		script.decrementIdent();
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_GET_FILE_FROM_REPO, null,
				Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH),
				"${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		params.clear();
		params.put(
				"file", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH) + "/" + Utils.getPropertyFormat(PROP_PATH)); //$NON-NLS-1$ //$NON-NLS-2$
		params.put(
				"tofile", Utils.getPropertyFormat(PROP_DESTINATIONFOLDER) + "/" + Utils.getPropertyFormat(PROP_FILE)); //$NON-NLS-1$ //$NON-NLS-2$
		params.put("failOnError", "false"); //$NON-NLS-1$ //$NON-NLS-2$
		script.printElement("copy", params); //$NON-NLS-1$
		script.printTargetEnd();

	}

	/**
	 * Generates a path where the specified repository should be cloned to.
	 * 
	 * @param repoLocation
	 * @return local file system path
	 */
	private String asLocalRepo(String repoLocation) {
		final StringBuffer b = new StringBuffer(repoLocation.length());
		b.append(Utils.getPropertyFormat(PROP_FETCH_CACHE_LOCATION))
				.append('/');
		for (int i = 0; i < repoLocation.length(); i++) {
			final char c = repoLocation.charAt(i);
			if (Character.isLetterOrDigit(c)) {
				b.append(c);
			} else {
				b.append('_');
			}
		}
		if (b.charAt(b.length() - 1) == '/')
			return b.substring(0, b.length() - 1);
		return b.toString();
	}

	/**
	 * Creates an SCMURL reference to the associated source.
	 * 
	 * @param repoLocation
	 * @param path
	 * @param projectName
	 * @return project reference string or <code>null</code> if none
	 */
	private String asReference(String repoLocation, String path,
			String projectName, String tagName) {
		final StringBuffer buffer = new StringBuffer();

		buffer.append(repoLocation);

		if (path != null) {
			final Path projectPath = new Path(path);

			buffer.append(projectPath.toString());
			buffer.append('"');

			if (!projectPath.lastSegment().equals(projectName)) {
				buffer.append(projectName);
				buffer.append('"');
			}
		}

			buffer.append(tagName);
		}
		return buffer.toString();
	}

	@Override
	public void generateRetrieveElementCall(Map entryInfos, IPath destination,
			IAntScript script) {
		final String type = (String) entryInfos.get(KEY_ELEMENT_TYPE);
		final boolean prebuilt = Boolean.valueOf(
				(String) entryInfos.get(KEY_PREBUILT)).booleanValue();
		final String gitRepo = (String) entryInfos.get(KEY_REPO);
		final String localGitRepo = asLocalRepo(gitRepo);
		final String path = (String) entryInfos.get(KEY_PATH);
		final String tag = (String) entryInfos
				.get(IFetchFactory.KEY_ELEMENT_TAG);

		printGitRepoBaseLocationDefault(script);

		final String gitCopyTarget;
		IPath locationToCheck = null;
		final Map<String, String> params = new HashMap<String, String>(5);
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		if (prebuilt) {
			params.put(PROP_DESTINATIONFOLDER, destination
					.removeLastSegments(1).toString());
			params.put(PROP_PATH, path);

			final String prebuiltJarFile = new Path(path).lastSegment();
			params.put(PROP_FILE, prebuiltJarFile);

			locationToCheck = destination.removeLastSegments(1).append(
					prebuiltJarFile);

			gitCopyTarget = TARGET_GET_FILE_FROM_REPO;
		} else {
			params.put(PROP_DESTINATIONFOLDER, destination.toString());
			if (path != null) {
				params.put(PROP_PATH, new Path(path).makeRelative().toString());
			}

			if (type.equals(ELEMENT_TYPE_FEATURE)) {
				locationToCheck = destination
						.append(Constants.FEATURE_FILENAME_DESCRIPTOR);
			} else if (type.equals(ELEMENT_TYPE_PLUGIN)) {
				locationToCheck = destination
						.append(Constants.PLUGIN_FILENAME_DESCRIPTOR);
			} else if (type.equals(ELEMENT_TYPE_FRAGMENT)) {
				locationToCheck = destination
						.append(Constants.FRAGMENT_FILENAME_DESCRIPTOR);
			} else if (type.equals(ELEMENT_TYPE_BUNDLE)) {
				locationToCheck = destination
						.append(Constants.BUNDLE_FILENAME_DESCRIPTOR);
			}

			gitCopyTarget = TARGET_GET_ELEMENT_FROM_REPO;
		}

		if (locationToCheck != null) {
			params.put(PROP_FILETOCHECK, locationToCheck.toString());
			printAvailableTask(locationToCheck.toString(),
					locationToCheck.toString(), script);
			if (!prebuilt
					&& (type.equals(IFetchFactory.ELEMENT_TYPE_PLUGIN) || type
							.equals(IFetchFactory.ELEMENT_TYPE_FRAGMENT))) {
				printAvailableTask(locationToCheck.toString(), destination
						.append(Constants.BUNDLE_FILENAME_DESCRIPTOR)
						.toString(), script);
			}
		}

		printCloneRepoAndCheckoutTagTasks(script, gitRepo, localGitRepo, tag,
				locationToCheck);

		script.printAntCallTask(gitCopyTarget, true, params);
	}

	@Override
	public void generateRetrieveFilesCall(final Map entryInfos,
			IPath destination, final String[] files, IAntScript script) {
		final String gitRepo = (String) entryInfos.get(KEY_REPO);
		final String localGitRepo = asLocalRepo(gitRepo);
		final String path = (String) entryInfos.get(KEY_PATH);
		final String tag = (String) entryInfos
				.get(IFetchFactory.KEY_ELEMENT_TAG);

		printGitRepoBaseLocationDefault(script);

		printCloneRepoAndCheckoutTagTasks(script, gitRepo, localGitRepo, tag,
				null);

		final Map<String, String> params = new HashMap<String, String>(4);
		for (int i = 0; i < files.length; i++) {
			final String file = files[i];
			IPath filePath;
			if (path != null) {
				filePath = new Path(path).append(file);
			} else {
				filePath = new Path((String) entryInfos.get(KEY_ELEMENT_NAME))
						.append(file);
			}

			params.clear();
			params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
			params.put(PROP_DESTINATIONFOLDER, destination.toString());
			params.put(PROP_PATH, filePath.toString());
			params.put(PROP_FILE, file);
			script.printAntCallTask(TARGET_GET_FILE_FROM_REPO, true, params);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void parseMapFileEntry(String repoSpecificentry,
			Properties overrideTags, Map entryInfos) throws CoreException {
		final String[] arguments = Utils.getArrayFromStringWithBlank(
				repoSpecificentry, SEPARATOR);
		final Map<String, String> table = new HashMap<String, String>();
		for (int i = 0; i < arguments.length; i++) {
			final String arg = arguments[i];
			final int index = arg.indexOf('=');
			if (index == -1) {
				final String message = NLS.bind(
						Messages.error_incorrectDirectoryEntryKeyValue,
						entryInfos.get(KEY_ELEMENT_NAME));
				throw new CoreException(new Status(IStatus.ERROR,
						IPDEBuildConstants.PI_PDEBUILD, 1, message, null));
			}
			final String key = arg.substring(0, index);
			final String value = arg.substring(index + 1);
			table.put(key, value);
		}

		if (!table.containsKey(KEY_REPO)) {
			final String message = NLS.bind(
					Messages.error_directoryEntryRequiresRepo,
					entryInfos.get(KEY_ELEMENT_NAME));
			throw new CoreException(new Status(IStatus.ERROR,
					IPDEBuildConstants.PI_PDEBUILD, 1, message, null));
		}

		final String overrideTag = overrideTags != null ? overrideTags
				.getProperty(OVERRIDE_TAG) : null;
		entryInfos
				.put(IFetchFactory.KEY_ELEMENT_TAG, (overrideTag != null
						&& overrideTag.trim().length() != 0 ? overrideTag
						: table.get(IFetchFactory.KEY_ELEMENT_TAG)));
		entryInfos.put(KEY_REPO, table.get(KEY_REPO));
		if (table.get(KEY_PATH) != null)
			entryInfos.put(KEY_PATH, new Path(table.get(KEY_PATH))
		entryInfos.put(KEY_PREBUILT, table.get(KEY_PREBUILT));
		addProjectReference(entryInfos);
	}

	private void printAvailableFile(IAntScript script, String file) {
	}

	/**
	 * Print the <code>available</code> Ant task to this script. This task sets
	 * a property value if the given file exists at runtime.
	 * 
	 * @param property
	 *            the property to set
	 * @param file
	 *            the file to look for
	 */
	private void printAvailableTask(String property, String file,
			IAntScript script) {
		final Map<String, String> params = new HashMap<String, String>(2);
		params.put("property", property); //$NON-NLS-1$
		params.put("file", file); //$NON-NLS-1$
		script.printElement("available", params); //$NON-NLS-1$
	}

	private void printCloneRepoAndCheckoutTagTasks(IAntScript script,
			String gitRepo, String localGitRepo, String tag,
			IPath locationToCheckIfPluginLocal) {
		printAvailableTask(localGitRepo, localGitRepo, script);

		final Map<String, String> params = new HashMap<String, String>(5);
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		if (locationToCheckIfPluginLocal != null)
			params.put(PROP_FILETOCHECK,
					locationToCheckIfPluginLocal.toString());
		script.printAntCallTask(TARGET_UPDATE_REPO, true, params);

		params.put(PROP_GITREPO, gitRepo);
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		if (locationToCheckIfPluginLocal != null)
			params.put(PROP_FILETOCHECK,
					locationToCheckIfPluginLocal.toString());
		script.printAntCallTask(TARGET_CLONE_REPO, true, params);

		params.clear();
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		params.put(PROP_TAG, tag);
		if (locationToCheckIfPluginLocal != null)
			params.put(PROP_FILETOCHECK,
					locationToCheckIfPluginLocal.toString());
		script.printAntCallTask(TARGET_CHECKOUT_TAG, true, params);

		printAvailableTask(localGitRepo, localGitRepo, script);
	}

	private void printConditionEnd(IAntScript script) {
		script.decrementIdent();
	}

	private void printConditionStart(IAntScript script, String property,
			String value, String elseValue) {
		script.printTabs();
		script.printAttribute("property", property, true); //$NON-NLS-1$
		script.printAttribute("value", value, false); //$NON-NLS-1$
		script.printAttribute("else", elseValue, false); //$NON-NLS-1$
		script.println();
		script.incrementIdent();
	}

	private void printGitRepoBaseLocationDefault(IAntScript script) {
	}

	private void printGitTask(IAntScript script, String commandName, List args) {
		final StringBuffer m = new StringBuffer();
		m.append(Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH));
		if (args != null) {
			for (int i = 0; i < args.size(); i++) {
			}
		}
		script.printEchoTask(null, m.toString(), "info"); //$NON-NLS-1$

		final Map<String, String> params = new HashMap<String, String>(3);
		params.put("executable", "git"); //$NON-NLS-1$ //$NON-NLS-2$
		params.put("dir", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH)); //$NON-NLS-1$
		params.put("failOnError", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		script.printStartTag("exec", params); //$NON-NLS-1$
		script.incrementIdent();

		printArg(script, commandName);

		if (args != null) {
			for (int i = 0; i < args.size(); i++) {
				final String arg = (String) args.get(i);
				printArg(script, arg);
			}
		}

		script.decrementIdent();
	}
}

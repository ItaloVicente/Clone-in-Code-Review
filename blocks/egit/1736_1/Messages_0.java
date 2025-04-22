package org.eclipse.egit.fetchfactory.internal;

import java.util.*;

import org.eclipse.core.runtime.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.pde.build.*;
import org.eclipse.pde.internal.build.*;

@SuppressWarnings("restriction")
public class GITFetchTaskFactory implements IFetchFactory {


	public static final String ID = "GIT"; //$NON-NLS-1$
	public static final String OVERRIDE_TAG = ID;

	private static final String TARGET_GET_ELEMENT_FROM_REPO = "GitFetchElementFromLocalRepo"; //$NON-NLS-1$
	private static final String TARGET_GET_FILE_FROM_REPO = "GitFetchFileFromLocalRepo"; //$NON-NLS-1$
	private static final String TARGET_CLONE_REPO = "GitCloneRepoToLocalRepo"; //$NON-NLS-1$
	private static final String TARGET_UPDATE_REPO = "GitUpdateLocalRepo"; //$NON-NLS-1$
	private static final String TARGET_CHECKOUT_TAG = "GitCheckoutTagInLocalRepo"; //$NON-NLS-1$
	private static final String SEPARATOR = ","; //$NON-NLS-1$

	private static final String KEY_REPO = "repo"; //$NON-NLS-1$
	private static final String KEY_PATH = "path"; //$NON-NLS-1$
	private static final String KEY_PREBUILT = "prebuilt"; //$NON-NLS-1$

	private static final String PROP_DESTINATIONFOLDER = "destinationFolder"; //$NON-NLS-1$
	private static final String PROP_GITREPO = "gitRepo"; //$NON-NLS-1$
	private static final String PROP_GITREPO_LOCAL_PATH = "gitRepoLocalPath"; //$NON-NLS-1$
	private static final String PROP_PATH = "path"; //$NON-NLS-1$
	private static final String PROP_FILE = "file"; //$NON-NLS-1$
	private static final String PROP_TAG = "tag"; //$NON-NLS-1$
	private static final String PROP_FILETOCHECK = "fileToCheck"; //$NON-NLS-1$

	public static String PROP_FETCH_CACHE_LOCATION = "fetchCacheLocation"; //$NON-NLS-1$
	public static String DEFAULT_FETCH_CACHE_LOCATION = "scmCache"; //$NON-NLS-1$

	private void addProjectReference(Map<String,String> entryInfos) {
		String repoLocation = (String) entryInfos.get(KEY_REPO);
		String path = (String) entryInfos.get(KEY_PATH);
		String projectName = (String) entryInfos.get(KEY_ELEMENT_NAME);
		String tag = (String) entryInfos.get(IFetchFactory.KEY_ELEMENT_TAG);

		if (repoLocation != null && projectName != null) {
			String sourceUrl = asReference(repoLocation, path, projectName, tag);
			if (sourceUrl != null) {
				entryInfos.put(Constants.KEY_SOURCE_REFERENCES, sourceUrl);
			}
		}
	}

	@Override
	public void addTargets(IAntScript script) {
		Map<String,String> params = new HashMap<String,String>(3);
		List<String> args = new ArrayList<String>(5);

		script.printComment("Start of common Git fetch factory targets."); //$NON-NLS-1$

		script.printTargetDeclaration("GitCheckSkipClone", null, null, null, null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		printConditionStart(script, "skipClone", null, null); //$NON-NLS-1$
		script.printStartTag("or"); //$NON-NLS-1$
		script.incrementIdent();
		printAvailableFile(script, Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH));
		printAvailableFile(script, Utils.getPropertyFormat(PROP_FILETOCHECK));
		script.decrementIdent();
		script.printEndTag("or"); //$NON-NLS-1$
		printConditionEnd(script);
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_CLONE_REPO, "GitCheckSkipClone", null, "skipClone", null); //$NON-NLS-1$ //$NON-NLS-2$
		printGitRepoBaseLocationDefault(script);
		params.put("dir", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH)); //$NON-NLS-1$
		script.printElement("mkdir", params); //$NON-NLS-1$
		args.add(Utils.getPropertyFormat(PROP_GITREPO));
		args.add("."); //$NON-NLS-1$
		printGitTask(script, "clone", args); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_UPDATE_REPO, null, Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH), "${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		args.clear();
		args.add("--all"); //$NON-NLS-1$
		printGitTask(script, "fetch", null); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_CHECKOUT_TAG, null, Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH), "${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		args.clear();
		args.add("--force"); //$NON-NLS-1$
		args.add(Utils.getPropertyFormat(PROP_TAG));
		printGitTask(script, "checkout", args); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_GET_ELEMENT_FROM_REPO, null, Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH), "${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		params.clear();
		params.put("todir", Utils.getPropertyFormat(PROP_DESTINATIONFOLDER)); //$NON-NLS-1$
		script.printStartTag("copy", params); //$NON-NLS-1$
		script.incrementIdent();
		params.clear();
		params.put("dir", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH) + "/" + Utils.getPropertyFormat(PROP_PATH)); //$NON-NLS-1$ //$NON-NLS-2$
		script.printElement("fileset", params); //$NON-NLS-1$
		script.decrementIdent();
		script.printEndTag("copy"); //$NON-NLS-1$
		script.printTargetEnd();

		script.printTargetDeclaration(TARGET_GET_FILE_FROM_REPO, null, Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH), "${fileToCheck}", null); //$NON-NLS-1$
		printGitRepoBaseLocationDefault(script);
		params.clear();
		params.put("file", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH) + "/" + Utils.getPropertyFormat(PROP_PATH)); //$NON-NLS-1$ //$NON-NLS-2$
		params.put("tofile", Utils.getPropertyFormat(PROP_DESTINATIONFOLDER) + "/" + Utils.getPropertyFormat(PROP_FILE)); //$NON-NLS-1$ //$NON-NLS-2$
		params.put("failOnError", "false"); //$NON-NLS-1$ //$NON-NLS-2$
		script.printElement("copy", params); //$NON-NLS-1$
		script.printTargetEnd();

		script.printComment("End of common Git fetch factory targets."); //$NON-NLS-1$
	}

	private void printGitRepoBaseLocationDefault(IAntScript script) {
		script.println("<property name=\"" + PROP_FETCH_CACHE_LOCATION + "\" value=\"" + DEFAULT_FETCH_CACHE_LOCATION + "\" />"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	private void printGitTask(IAntScript script, String commandName, List args) {
		StringBuffer m = new StringBuffer();
		m.append("[GIT] "); //$NON-NLS-1$
		m.append(Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH));
		m.append(" >> git ").append(commandName); //$NON-NLS-1$
		if (args != null) {
			for (int i = 0; i < args.size(); i++) {
				m.append(" ").append(args.get(i)); //$NON-NLS-1$
			}
		}
		script.printEchoTask(null, m.toString(), "info"); //$NON-NLS-1$

		Map<String,String> params = new HashMap<String,String>(3);
		params.put("executable", "git"); //$NON-NLS-1$ //$NON-NLS-2$
		params.put("dir", Utils.getPropertyFormat(PROP_GITREPO_LOCAL_PATH)); //$NON-NLS-1$
		params.put("failOnError", "true"); //$NON-NLS-1$ //$NON-NLS-2$
		script.printStartTag("exec", params); //$NON-NLS-1$
		script.incrementIdent();

		printArg(script, commandName);

		if (args != null) {
			for (int i = 0; i < args.size(); i++) {
				String arg = (String) args.get(i);
				printArg(script, arg);
			}
		}

		script.decrementIdent();
		script.printEndTag("exec"); //$NON-NLS-1$
	}

	private static void printArg(IAntScript script, String value) {
		Map<String,String> params = new HashMap<String,String>(1);
		params.put("value", value); //$NON-NLS-1$
		script.printElement("arg", params); //$NON-NLS-1$
	}

	private String asLocalRepo(String repoLocation) {
		StringBuffer b = new StringBuffer(repoLocation.length());
		b.append(Utils.getPropertyFormat(PROP_FETCH_CACHE_LOCATION)).append('/');
		for (int i = 0; i < repoLocation.length(); i++) {
			char c = repoLocation.charAt(i);
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

	private String asReference(String repoLocation, String path, String projectName, String tagName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("scm:git:"); //$NON-NLS-1$

		buffer.append(repoLocation);

		if (path != null) {
			Path projectPath = new Path(path);

			buffer.append(";path=\""); //$NON-NLS-1$
			buffer.append(projectPath.toString());
			buffer.append('"');

			if (!projectPath.lastSegment().equals(projectName)) {
				buffer.append(";project=\""); //$NON-NLS-1$
				buffer.append(projectName);
				buffer.append('"');
			}
		}

		if (tagName != null && !tagName.equals("HEAD")) { //$NON-NLS-1$
			buffer.append(";tag="); //$NON-NLS-1$
			buffer.append(tagName);
		}
		return buffer.toString();
	}

	@Override
	public void generateRetrieveElementCall(Map entryInfos, IPath destination, IAntScript script) {
		String type = (String) entryInfos.get(KEY_ELEMENT_TYPE);
		boolean prebuilt = Boolean.valueOf((String) entryInfos.get(KEY_PREBUILT)).booleanValue();
		String gitRepo = (String) entryInfos.get(KEY_REPO);
		String localGitRepo = asLocalRepo(gitRepo);
		String path = (String) entryInfos.get(KEY_PATH);
		String tag = (String) entryInfos.get(IFetchFactory.KEY_ELEMENT_TAG);

		final String gitCopyTarget;
		IPath locationToCheck = null;
		Map<String,String> params = new HashMap<String,String>(5);
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		if (prebuilt) {
			params.put(PROP_DESTINATIONFOLDER, destination.removeLastSegments(1).toString());
			params.put(PROP_PATH, path);

			String prebuiltJarFile = new Path(path).lastSegment();
			params.put(PROP_FILE, prebuiltJarFile);

			locationToCheck = destination.removeLastSegments(1).append(prebuiltJarFile);

			gitCopyTarget = TARGET_GET_FILE_FROM_REPO;
		} else {
			params.put(PROP_DESTINATIONFOLDER, destination.toString());
			if (path != null) {
				params.put(PROP_PATH, new Path(path).makeRelative().toString());
			}

			if (type.equals(ELEMENT_TYPE_FEATURE)) {
				locationToCheck = destination.append(Constants.FEATURE_FILENAME_DESCRIPTOR);
			} else if (type.equals(ELEMENT_TYPE_PLUGIN)) {
				locationToCheck = destination.append(Constants.PLUGIN_FILENAME_DESCRIPTOR);
			} else if (type.equals(ELEMENT_TYPE_FRAGMENT)) {
				locationToCheck = destination.append(Constants.FRAGMENT_FILENAME_DESCRIPTOR);
			} else if (type.equals(ELEMENT_TYPE_BUNDLE)) {
				locationToCheck = destination.append(Constants.BUNDLE_FILENAME_DESCRIPTOR);
			}

			gitCopyTarget = TARGET_GET_ELEMENT_FROM_REPO;
		}

		if (locationToCheck != null) {
			params.put(PROP_FILETOCHECK, locationToCheck.toString());
			printAvailableTask(locationToCheck.toString(), locationToCheck.toString(), script);
			if (!prebuilt && (type.equals(IFetchFactory.ELEMENT_TYPE_PLUGIN) || type.equals(IFetchFactory.ELEMENT_TYPE_FRAGMENT))) {
				printAvailableTask(locationToCheck.toString(), destination.append(Constants.BUNDLE_FILENAME_DESCRIPTOR).toString(), script);
			}
		}

		printCloneRepoAndCheckoutTagTasks(script, gitRepo, localGitRepo, tag, locationToCheck);

		script.printAntCallTask(gitCopyTarget, true, params);
	}

	@Override
	public void generateRetrieveFilesCall(final Map entryInfos, IPath destination, final String[] files, IAntScript script) {
		String gitRepo = (String) entryInfos.get(KEY_REPO);
		String localGitRepo = asLocalRepo(gitRepo);
		String path = (String) entryInfos.get(KEY_PATH);
		String tag = (String) entryInfos.get(IFetchFactory.KEY_ELEMENT_TAG);

		printCloneRepoAndCheckoutTagTasks(script, gitRepo, localGitRepo, tag, null);

		Map<String,String> params = new HashMap<String,String>(4);
		for (int i = 0; i < files.length; i++) {
			String file = files[i];
			IPath filePath;
			if (path != null) {
				filePath = new Path(path).append(file);
			} else {
				filePath = new Path((String) entryInfos.get(KEY_ELEMENT_NAME)).append(file);
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
	public void parseMapFileEntry(String repoSpecificentry, Properties overrideTags, Map entryInfos) throws CoreException {
		String[] arguments = Utils.getArrayFromStringWithBlank(repoSpecificentry, SEPARATOR);
		Map<String,String> table = new HashMap<String,String>();
		for (int i = 0; i < arguments.length; i++) {
			String arg = arguments[i];
			int index = arg.indexOf('=');
			if (index == -1) {
				String message = NLS.bind(Messages.error_incorrectDirectoryEntryKeyValue, entryInfos.get(KEY_ELEMENT_NAME));
				throw new CoreException(new Status(IStatus.ERROR, IPDEBuildConstants.PI_PDEBUILD, 1, message, null));
			}
			String key = arg.substring(0, index);
			String value = arg.substring(index + 1);
			table.put(key, value);
		}

		if (!table.containsKey(KEY_REPO)) {
			String message = NLS.bind(Messages.error_directoryEntryRequiresRepo, entryInfos.get(KEY_ELEMENT_NAME));
			throw new CoreException(new Status(IStatus.ERROR, IPDEBuildConstants.PI_PDEBUILD, 1, message, null));
		}

		String overrideTag = overrideTags != null ? overrideTags.getProperty(OVERRIDE_TAG) : null;
		entryInfos.put(IFetchFactory.KEY_ELEMENT_TAG, (overrideTag != null && overrideTag.trim().length() != 0 ? overrideTag : table.get(IFetchFactory.KEY_ELEMENT_TAG)));
		entryInfos.put(KEY_REPO, table.get(KEY_REPO));
		if (table.get(KEY_PATH) != null)
			entryInfos.put(KEY_PATH, new Path((String) table.get(KEY_PATH)).makeRelative().removeTrailingSeparator().toString()); // sanitize path
		entryInfos.put(KEY_PREBUILT, table.get(KEY_PREBUILT));
		addProjectReference(entryInfos);
	}

	private void printAvailableTask(String property, String file, IAntScript script) {
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("property", property); //$NON-NLS-1$
		params.put("file", file); //$NON-NLS-1$
		script.printElement("available", params); //$NON-NLS-1$
	}

	private void printCloneRepoAndCheckoutTagTasks(IAntScript script, String gitRepo, String localGitRepo, String tag, IPath locationToCheckIfPluginLocal) {
		printAvailableTask(localGitRepo, localGitRepo, script);

		Map<String, String>  params = new HashMap<String, String>(5);
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		if (locationToCheckIfPluginLocal != null)
			params.put(PROP_FILETOCHECK, locationToCheckIfPluginLocal.toString());
		script.printAntCallTask(TARGET_UPDATE_REPO, true, params);

		params.put(PROP_GITREPO, gitRepo);
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		if (locationToCheckIfPluginLocal != null)
			params.put(PROP_FILETOCHECK, locationToCheckIfPluginLocal.toString());
		script.printAntCallTask(TARGET_CLONE_REPO, true, params);

		params.clear();
		params.put(PROP_GITREPO_LOCAL_PATH, localGitRepo);
		params.put(PROP_TAG, tag);
		if (locationToCheckIfPluginLocal != null)
			params.put(PROP_FILETOCHECK, locationToCheckIfPluginLocal.toString());
		script.printAntCallTask(TARGET_CHECKOUT_TAG, true, params);

		printAvailableTask(localGitRepo, localGitRepo, script);
	}

	private void printConditionStart(IAntScript script, String property, String value, String elseValue) {
		script.printTabs();
		script.print("<condition"); //$NON-NLS-1$
		script.printAttribute("property", property, true); //$NON-NLS-1$
		script.printAttribute("value", value, false); //$NON-NLS-1$
		script.printAttribute("else", elseValue, false); //$NON-NLS-1$
		script.print(">"); //$NON-NLS-1$
		script.println();
		script.incrementIdent();
	}

	private void printAvailableFile(IAntScript script, String file) {
		script.println("<available file=\"" + file + "\"/>"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private void printConditionEnd(IAntScript script) {
		script.decrementIdent();
		script.printEndTag("condition"); //$NON-NLS-1$
	}
}

package org.eclipse.egit.ui.internal.branch.tracker;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;

class ProjectTrackerPreferenceHelper {

	private static final String PREFIX = "BranchProjectTracker_"; //$NON-NLS-1$

	private static final String KEY_PROJECTS = "projects"; //$NON-NLS-1$

	private static final String KEY_PROJECT = "project"; //$NON-NLS-1$

	static void saveToPreferences(Repository repo, String branch,
			List<String> projects)
	{
		XMLMemento preferencesMemento = createXMLMemento(projects);
		String preferenceKey = getPreferenceKey(repo, branch);
		saveToPreferenceStore(preferenceKey, preferencesMemento);
	}

	private static XMLMemento createXMLMemento(List<String> projectPaths) {

		XMLMemento memento = XMLMemento.createWriteRoot(KEY_PROJECTS);

		projectPaths.forEach(path -> {
			IMemento child = memento.createChild(KEY_PROJECT);
			child.putTextData(path);
		});

		return memento;
	}

	static String getPreferenceKey(Repository repo,
			final String branch) {
		return PREFIX + '_' + repo.getDirectory().getAbsolutePath() + '_'
				+ branch;
	}

	private static void saveToPreferenceStore(String key, XMLMemento content)
	{
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		StringWriter writer = new StringWriter();
		try {
			content.save(writer);
			store.setValue(key, writer.toString());
		} catch (IOException e) {
			Activator.logError("Error writing branch-project associations", e); //$NON-NLS-1$
		}
	}

	static List<String> restoreFromPreferences(Repository repo,
			String branch) {

		String key = getPreferenceKey(repo, branch);
		String value = Activator.getDefault().getPreferenceStore()
				.getString(key);

		if (value.length() == 0)
			return Collections.emptyList();

		XMLMemento memento;
		try {
			memento = XMLMemento.createReadRoot(new StringReader(value));
		} catch (WorkbenchException e) {
			Activator.logError("Error reading branch-project associations", e); //$NON-NLS-1$
			return Collections.<String> emptyList();
		}
		IMemento[] children = memento.getChildren(KEY_PROJECT);
		if (children.length == 0)
			return Collections.<String> emptyList();

		List<String> projectPaths = Stream.of(children) //
				.map(child -> child.getTextData()) //
				.filter(x -> !StringUtils.isEmptyOrNull(x)) //
				.collect(Collectors.toList());
		return projectPaths;
	}
}

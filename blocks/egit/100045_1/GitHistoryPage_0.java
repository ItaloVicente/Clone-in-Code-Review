package org.eclipse.egit.ui.internal.components;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jgit.lib.Repository;

public final class RepositoryMenuUtil {

	private RepositoryMenuUtil() {
	}

	public static void fillRepositories(IMenuManager menuManager,
			boolean includeBare, Consumer<Repository> action) {
		RepositoryUtil util = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryUtil();
		RepositoryCache cache = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache();
		Set<String> repositories = util.getRepositories();
		Map<String, Set<File>> repos = new HashMap<>();
		for (String repo : repositories) {
			File gitDir = new File(repo);
			String name = null;
			try {
				Repository r = cache.lookupRepository(gitDir);
				if (!includeBare && r.isBare()) {
					continue;
				}
				name = util.getRepositoryName(r);
			} catch (IOException e) {
				continue;
			}
			Set<File> files = repos.get(name);
			if (files == null) {
				files = new HashSet<>();
				files.add(gitDir);
				repos.put(name, files);
			} else {
				files.add(gitDir);
			}
		}
		String[] repoNames = repos.keySet().toArray(new String[repos.size()]);
		Arrays.sort(repoNames);
		for (String repoName : repoNames) {
			Set<File> files = repos.get(repoName);
			File[] gitDirs = files.toArray(new File[files.size()]);
			Arrays.sort(gitDirs);
			for (File f : gitDirs) {
				IAction menuItem = new Action(repoName) {
					@Override
					public void run() {
						try {
							Repository r = cache.lookupRepository(f);
							action.accept(r);
						} catch (IOException e) {
							Activator.showError(e.getLocalizedMessage(), e);
						}
					}
				};
				menuItem.setToolTipText(f.getPath());
				menuManager.add(menuItem);
			}
		}
	}
}

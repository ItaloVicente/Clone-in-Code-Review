package org.eclipse.egit.ui.internal.components;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.egit.core.RepositoryCache;
import org.eclipse.egit.core.RepositoryUtil;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public final class RepositoryMenuUtil {

	private RepositoryMenuUtil() {
	}

	public static void fillRepositories(@NonNull IMenuManager menuManager,
			boolean includeBare, @Nullable File currentRepoDir,
			@NonNull Consumer<Repository> action) {
		for (IAction item : getRepositoryActions(includeBare, currentRepoDir,
				action)) {
			menuManager.add(item);
		}
	}

	public static Collection<IAction> getRepositoryActions(boolean includeBare,
			@Nullable File currentRepoDir,
			@NonNull Consumer<Repository> action) {
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
		Arrays.sort(repoNames, CommonUtils.STRING_ASCENDING_COMPARATOR);
		List<IAction> result = new ArrayList<>();
		for (String repoName : repoNames) {
			Set<File> files = repos.get(repoName);
			File[] gitDirs = files.toArray(new File[files.size()]);
			Arrays.sort(gitDirs);
			for (File f : gitDirs) {
				IAction menuItem = new Action(repoName,
						IAction.AS_RADIO_BUTTON) {
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
				if (f.equals(currentRepoDir)) {
					menuItem.setChecked(true);
				}
				result.add(menuItem);
			}
		}
		return result;
	}

	public static class RepositoryToolbarAction extends Action
			implements IWorkbenchAction, IMenuCreator {

		private final RepositoryUtil util = org.eclipse.egit.core.Activator
				.getDefault().getRepositoryUtil();

		private final IEclipsePreferences preferences = util.getPreferences();

		private final IPreferenceChangeListener listener;

		private final @NonNull Consumer<Repository> action;

		private final @NonNull Supplier<Repository> currentRepo;

		private final boolean includeBare;

		private Menu menu;

		private boolean showMenu;

		public RepositoryToolbarAction(boolean includeBare,
				@NonNull Supplier<Repository> currentRepo,
				@NonNull Consumer<Repository> action) {
			this(UIText.RepositoryToolbarAction_label, UIIcons.REPOSITORY,
					UIText.RepositoryToolbarAction_tooltip, includeBare,
					currentRepo, action);
		}

		public RepositoryToolbarAction(String text,
				@Nullable ImageDescriptor image, @Nullable String tooltip,
				boolean includeBare, @NonNull Supplier<Repository> currentRepo,
				@NonNull Consumer<Repository> action) {
			super(text, IAction.AS_DROP_DOWN_MENU);
			setImageDescriptor(image);
			setToolTipText(tooltip == null ? text : tooltip);
			this.includeBare = includeBare;
			this.currentRepo = currentRepo;
			this.action = action;
			this.listener = event -> {
				if (RepositoryUtil.PREFS_DIRECTORIES_REL
						.equals(event.getKey())) {
					setEnabled(!util.getRepositories().isEmpty());
				}
			};
			setEnabled(!util.getRepositories().isEmpty());
			preferences.addPreferenceChangeListener(listener);
		}

		@Override
		public void run() {
			showMenu = true;
		}

		@Override
		public void runWithEvent(Event event) {
			if (!isEnabled()) {
				return;
			}
			showMenu = false;
			run();
			Widget widget = event.widget;
			if (showMenu && (widget instanceof ToolItem)) {
				ToolItem item = (ToolItem) widget;
				Rectangle bounds = item.getBounds();
				event.detail = SWT.ARROW;
				event.x = bounds.x;
				event.y = bounds.y + bounds.height;
				item.notifyListeners(SWT.Selection, event);
			}
		}

		@Override
		public IMenuCreator getMenuCreator() {
			return this;
		}

		@Override
		public Menu getMenu(Control parent) {
			if (menu != null) {
				menu.dispose();
				menu = null;
			}
			if (isEnabled()) {
				Repository current = currentRepo.get();
				File gitDir = current == null ? null : current.getDirectory();
				Collection<IAction> actions = RepositoryMenuUtil
						.getRepositoryActions(includeBare, gitDir, action);
				menu = new Menu(parent);
				for (IAction a : actions) {
					ActionContributionItem item = new ActionContributionItem(a);
					item.fill(menu, -1);
				}
			}
			return menu;
		}

		@Override
		public Menu getMenu(Menu parent) {
			return null;
		}

		@Override
		public void dispose() {
			if (menu != null) {
				menu.dispose();
				menu = null;
			}
			preferences.removePreferenceChangeListener(listener);
		}
	}
}

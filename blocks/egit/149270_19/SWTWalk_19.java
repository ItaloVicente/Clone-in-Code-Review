package org.eclipse.egit.ui.internal.history;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.tools.ant.types.selectors.TokenizedPath;
import org.apache.tools.ant.types.selectors.TokenizedPattern;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;

public class RefFilterHelper {

	private static final String REF_SEPERATOR = ":"; //$NON-NLS-1$

	private static final String MACRO_CURRENT_BRANCH = "[CURRENT-BRANCH]"; //$NON-NLS-1$

	private static final String DEFAULT_SELECTED_REFS = Constants.HEAD;

	private static final String DEFAULT_SELECTED_REFS_ALL_BRANCHES = Constants.HEAD
			+ REF_SEPERATOR + Constants.R_HEADS + REF_SEPERATOR
			+ Constants.R_REMOTES + REF_SEPERATOR + Constants.R_TAGS;

	private final IPreferenceStore store;

	@NonNull
	private final Repository repository;

	private List<RefFilter> preconfiguredFilters;

	private List<RefFilter> filtersForHEAD;
	private List<RefFilter> filtersForCurrentBranch;
	private List<RefFilter> filtersForAllBranchesAndTags;

	private Map<String, Function<Repository, String>> macros;

	private static @NonNull IPreferenceStore checkNull(IPreferenceStore store) {
		if (store == null)
			throw new NullPointerException("Preference store is null."); //$NON-NLS-1$
		return store;
	}

	public RefFilterHelper(@NonNull Repository repository) {
		this(repository,
				checkNull(Activator.getDefault().getPreferenceStore()));
	}

	public RefFilterHelper(@NonNull Repository repository,
			@NonNull
			IPreferenceStore store) {
		this.repository = repository;
		this.store = store;
		setupPreconfigueredFilters();
		setupMacros();
		initDefaultsForRepo();
	}

	private RefFilter newPreConfFilter(String filter) {
		return new RefFilter(filter, true);
	}

	private RefFilter newPreConfPrefixFilter(String prefix) {
		return newPreConfFilter(prefix + "**"); //$NON-NLS-1$
	}

	private void setupPreconfigueredFilters() {
		preconfiguredFilters = new ArrayList<>();
		filtersForHEAD = new ArrayList<>();
		filtersForCurrentBranch = new ArrayList<>();
		filtersForAllBranchesAndTags = new ArrayList<>();

		RefFilter head = newPreConfFilter(Constants.HEAD);
		preconfiguredFilters.add(head);
		filtersForHEAD.add(head);
		filtersForAllBranchesAndTags.add(head);

		RefFilter current_branch = newPreConfFilter(
				Constants.R_REFS + "**/" + MACRO_CURRENT_BRANCH); //$NON-NLS-1$
		preconfiguredFilters.add(current_branch);
		filtersForCurrentBranch.add(current_branch);

		RefFilter branches = newPreConfPrefixFilter(Constants.R_HEADS);
		preconfiguredFilters.add(branches);
		filtersForAllBranchesAndTags.add(branches);

		RefFilter remoteBranches = newPreConfPrefixFilter(Constants.R_REMOTES);
		preconfiguredFilters.add(remoteBranches);
		filtersForAllBranchesAndTags.add(remoteBranches);

		RefFilter tags = newPreConfPrefixFilter(Constants.R_TAGS);
		preconfiguredFilters.add(tags);
		filtersForAllBranchesAndTags.add(tags);
	}

	private void setupMacros() {
		macros = new HashMap<>();
		macros.put(MACRO_CURRENT_BRANCH, repo -> {
			try {
				return repo.getBranch();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
			}
			return ""; //$NON-NLS-1$
		});
	}

	private void setDefaultSelectionBasedOnShowAllBranches() {
		String currentDefault = store.getDefaultString(
				UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS);

		if (currentDefault != DEFAULT_SELECTED_REFS
				&& currentDefault != DEFAULT_SELECTED_REFS_ALL_BRANCHES) {
			return;
		}

		boolean showAll = store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES);

		if (showAll) {
			store.setDefault(UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS,
					DEFAULT_SELECTED_REFS_ALL_BRANCHES);
		} else {
			store.setDefault(UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS,
					DEFAULT_SELECTED_REFS);
		}
	}

	private void initDefaultForRepo(String preferenceName) {
		String repoSepcificPrefName = Activator.getDefault().getRepositoryUtil()
				.getRepositorySpecificPreferenceKey(this.repository,
						preferenceName);

		store.setDefault(repoSepcificPrefName,
				store.getDefaultString(preferenceName));
	}

	private void initDefaultsForRepo() {
		setDefaultSelectionBasedOnShowAllBranches();
		initDefaultForRepo(UIPreferences.RESOURCEHISTORY_REF_FILTERS);
		initDefaultForRepo(UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS);
		initDefaultForRepo(
				UIPreferences.RESOURCEHISTORY_LAST_SELECTED_REF_FILTERS);
	}

	protected String getPreferenceString(String preferenceName) {
		String repoSepcificPrefName = Activator.getDefault().getRepositoryUtil()
				.getRepositorySpecificPreferenceKey(this.repository,
						preferenceName);

		return store.getString(repoSepcificPrefName);
	}

	private List<String> getFiltersFromPref(String preferenceName) {
		String refFiltersString = getPreferenceString(preferenceName);
		String[] filters = refFiltersString.split(REF_SEPERATOR);

		return Arrays.asList(filters);
	}

	private void savePreferencStoreIfNeeded() {
		if (store.needsSaving()
				&& store instanceof IPersistentPreferenceStore) {
			try {
				((IPersistentPreferenceStore) store).save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
			}
		}
	}

	private void setFiltersInPref(String preferenceName, List<String> filters,
			boolean save) {
		String repoSepcificPrefName = Activator.getDefault().getRepositoryUtil()
				.getRepositorySpecificPreferenceKey(this.repository,
						preferenceName);
		String refFiltersString = String.join(REF_SEPERATOR, filters);
		store.setValue(repoSepcificPrefName, refFiltersString);

		if (save) {
			savePreferencStoreIfNeeded();
		}
	}

	public List<String> getConfiguredFilters() {
		return getFiltersFromPref(UIPreferences.RESOURCEHISTORY_REF_FILTERS);
	}

	public void setConfiguredFilters(List<String> filters, boolean save) {
		setFiltersInPref(UIPreferences.RESOURCEHISTORY_REF_FILTERS, filters,
				save);
	}

	public List<String> getSelectedFilters() {
		return getFiltersFromPref(
				UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS);
	}

	public void setSelectedFilters(List<String> filters, boolean save) {
		setFiltersInPref(UIPreferences.RESOURCEHISTORY_SELECTED_REF_FILTERS,
				filters, save);
	}

	public List<String> getLastSelectedFilters() {
		return getFiltersFromPref(
				UIPreferences.RESOURCEHISTORY_LAST_SELECTED_REF_FILTERS);
	}

	public void setLastSelectedFilters(List<String> filters, boolean save) {
		setFiltersInPref(
				UIPreferences.RESOURCEHISTORY_LAST_SELECTED_REF_FILTERS,
				filters, save);
	}

	private void addPreconfigueredFilters(Map<String, RefFilter> filters) {
		for (RefFilter filter : preconfiguredFilters) {
			filters.put(filter.getFilterString(), new RefFilter(filter));
		}
	}

	public Set<RefFilter> getRefFilters() {
		Map<String, RefFilter> filters = new HashMap<>();
		addPreconfigueredFilters(filters);

		for (String filter : getConfiguredFilters()) {
			if (filter == null || filter.isEmpty()) {
				continue;
			}
			filters.put(filter, new RefFilter(filter, false));
		}

		for (String filter : getSelectedFilters()) {
			if (filter == null || filter.isEmpty()) {
				continue;
			}
			filters.putIfAbsent(filter, new RefFilter(filter, false));
			filters.get(filter).setSelected(true);
		}
		return new HashSet<>(filters.values());
	}

	public void restoreLastSelectionState(Set<RefFilter> filters) {
		for(RefFilter filter : filters) {
			filter.setSelected(getLastSelectedFilters()
					.contains(filter.getFilterString()));
		}
	}

	public void setRefFilters(Set<RefFilter> filters) {
		List<String> selected = filters.stream().filter(RefFilter::isSelected)
				.map(RefFilter::getFilterString).collect(Collectors.toList());
		setSelectedFilters(selected, false);

		List<String> configured = filters.stream()
				.filter(f -> !f.isPreconfigured())
				.map(RefFilter::getFilterString).collect(Collectors.toList());
		setConfiguredFilters(configured, false);

		savePreferencStoreIfNeeded();
	}

	public void saveSelectionStateAsLastSelectionState(Set<RefFilter> filters) {
		List<String> selected = new ArrayList<>();
		for(RefFilter filter : filters) {
			if (filter.isSelected()) {
				selected.add(filter.getFilterString());
			}
		}
		setLastSelectedFilters(selected, true);
	}

	public void resetLastSelectionStateToDefault() {
		String repoSepcificPrefName = Activator.getDefault().getRepositoryUtil()
				.getRepositorySpecificPreferenceKey(this.repository,
						UIPreferences.RESOURCEHISTORY_LAST_SELECTED_REF_FILTERS);
		store.setToDefault(repoSepcificPrefName);
		savePreferencStoreIfNeeded();
	}

	public Set<Ref> getMatchingRefsForSelectedRefFilters()
			throws IOException {
		RefDatabase db = this.repository.getRefDatabase();
		Set<Ref> result = new HashSet<>();
		Set<RefFilter> selectedFilters = getRefFilters().stream()
				.filter(f -> f.isSelected()).collect(Collectors.toSet());

		for (Ref ref : db.getRefs()) {
			TokenizedPath refPath = new TokenizedPath(ref.getName());
			for (RefFilter filter : selectedFilters) {
				if (filter.matches(refPath)) {
					result.add(ref);
					break;
				}
			}
		}

		return result;
	}

	public void selectOnlyHEAD(Set<RefFilter> filters) {
		for (RefFilter filter : filters) {
			filter.setSelected(filtersForHEAD.contains(filter));
		}
	}

	public boolean isOnlyHEADSelected(Set<RefFilter> filters) {
		for (RefFilter filter : filters) {
			if (filter.isSelected()) {
				if (!filtersForHEAD.contains(filter)) {
					return false;
				}
			} else {
				if (filtersForHEAD.contains(filter)) {
					return false;
				}
			}
		}
		return true;
	}

	public void selectOnlyCurrentBranch(Set<RefFilter> filters) {
		for (RefFilter filter : filters) {
			filter.setSelected(filtersForCurrentBranch.contains(filter));
		}
	}

	public boolean isOnlyCurrentBranchSelected(Set<RefFilter> filters) {
		for (RefFilter filter : filters) {
			if (filter.isSelected()) {
				if (!filtersForCurrentBranch.contains(filter)) {
					return false;
				}
			} else {
				if (filtersForCurrentBranch.contains(filter)) {
					return false;
				}
			}
		}
		return true;
	}

	public void selectExactlyAllBranchesAndTags(Set<RefFilter> filters) {
		for (RefFilter filter : filters) {
			filter.setSelected(filtersForAllBranchesAndTags.contains(filter));
		}
	}

	public boolean isExactlyAllBranchesAndTagsSelected(Set<RefFilter> filters) {
		for (RefFilter filter : filters) {
			if (filter.isSelected()) {
				if (!filtersForAllBranchesAndTags.contains(filter))
					return false;
			} else {
				if (filtersForAllBranchesAndTags.contains(filter))
					return false;
			}
		}
		return true;
	}

	public Set<RefFilter> getDefaults() {
		setDefaultSelectionBasedOnShowAllBranches();
		RefFilterHelper defaultsHelper = new RefFilterHelper(this.repository) {
			@Override
			protected String getPreferenceString(String preferenceName) {
				return store.getDefaultString(preferenceName);
			}
		};
		return defaultsHelper.getRefFilters();
	}

	public class RefFilter {
		private final boolean preconfigured;

		private String filterString;
		private TokenizedPattern filterPattern;

		private boolean selected = false;

		public RefFilter(RefFilter original) {
			this(original.getFilterString(), original.isPreconfigured());
			this.selected = original.isSelected();
		}

		public RefFilter(String filterString) {
			this(filterString, false);
		}

		private RefFilter(String filterString, boolean isPreconfigured) {
			if (filterString == null || filterString.isEmpty())
				throw new IllegalArgumentException(
						"Filter string is null or empty."); //$NON-NLS-1$
			this.filterString = filterString;
			this.filterPattern = new TokenizedPattern(filterString);
			this.preconfigured = isPreconfigured;
		}

		public boolean isPreconfigured() {
			return preconfigured;
		}

		private TokenizedPattern patternWithExpandedMacros() {
			TokenizedPattern currentPattern = filterPattern;
			for(Map.Entry<String, Function<Repository, String>> macro : macros.entrySet()) {
				if (currentPattern.containsPattern(macro.getKey())) {
					String oldString = currentPattern.getPattern();
					String macroString = macro.getKey();
					String replacingString = macro.getValue().apply(repository);
					String newString = oldString.replace(macroString,
							replacingString);
					currentPattern = new TokenizedPattern(newString);
				}
			}
			return currentPattern;
		}

		public boolean matches(TokenizedPath refPath) {
			return patternWithExpandedMacros().matchPath(refPath,
					true);
		}

		public String getFilterString() {
			return this.filterString;
		}

		public void setFilterString(String filterString) {
			if (filterString == null || filterString.isEmpty()) {
				throw new IllegalArgumentException(
						"Filter string is null or empty."); //$NON-NLS-1$
			}
			if (preconfigured) {
				throw new IllegalStateException(
						"Cannot change a preconfigured filter."); //$NON-NLS-1$
			}
			this.filterString = filterString;
			this.filterPattern = new TokenizedPattern(filterString);
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		@Override
		public int hashCode() {
			return filterPattern.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof RefFilter))
				return false;
			return filterPattern.equals(((RefFilter) obj).filterPattern);
		}
	}
}

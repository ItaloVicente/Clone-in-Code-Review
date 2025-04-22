package org.eclipse.e4.ui.dialogs.filteredtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.ibm.icu.text.BreakIterator;

public class PatternFilter extends ViewerFilter {
	private Map cache = new HashMap();

	private Map foundAnyCache = new HashMap();

	private boolean useCache = false;

	private boolean includeLeadingWildcard = false;

	private StringMatcher matcher;

	private boolean useEarlyReturnIfMatcherIsNull = true;

	private static Object[] EMPTY = new Object[0];

	@Override
	public final Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		if (matcher == null && useEarlyReturnIfMatcherIsNull) {
			return elements;
		}

		if (!useCache) {
			return super.filter(viewer, parent, elements);
		}

		Object[] filtered = (Object[]) cache.get(parent);
		if (filtered == null) {
			Boolean foundAny = (Boolean) foundAnyCache.get(parent);
			if (foundAny != null && !foundAny.booleanValue()) {
				filtered = EMPTY;
			} else {
				filtered = super.filter(viewer, parent, elements);
			}
			cache.put(parent, filtered);
		}
		return filtered;
	}

	private boolean isAnyVisible(Viewer viewer, Object parent, Object[] elements) {
		if (matcher == null) {
			return true;
		}

		if (!useCache) {
			return computeAnyVisible(viewer, elements);
		}

		Object[] filtered = (Object[]) cache.get(parent);
		if (filtered != null) {
			return filtered.length > 0;
		}
		Boolean foundAny = (Boolean) foundAnyCache.get(parent);
		if (foundAny == null) {
			foundAny = computeAnyVisible(viewer, elements) ? Boolean.TRUE
					: Boolean.FALSE;
			foundAnyCache.put(parent, foundAny);
		}
		return foundAny.booleanValue();
	}

	private boolean computeAnyVisible(Viewer viewer, Object[] elements) {
		boolean elementFound = false;
		for (int i = 0; i < elements.length && !elementFound; i++) {
			Object element = elements[i];
			elementFound = isElementVisible(viewer, element);
		}
		return elementFound;
	}

	@Override
	public final boolean select(Viewer viewer, Object parentElement,
			Object element) {
		return isElementVisible(viewer, element);
	}

	public final void setIncludeLeadingWildcard(
			final boolean includeLeadingWildcard) {
		this.includeLeadingWildcard = includeLeadingWildcard;
	}

	public void setPattern(String patternString) {
		if ("org.eclipse.ui.keys.optimization.true".equals(patternString)) { //$NON-NLS-1$
			useEarlyReturnIfMatcherIsNull = true;
			return;
		} else if ("org.eclipse.ui.keys.optimization.false".equals(patternString)) { //$NON-NLS-1$
			useEarlyReturnIfMatcherIsNull = false;
			return;
		}
		clearCaches();
		if (patternString == null || patternString.equals("")) { //$NON-NLS-1$
			matcher = null;
		} else {
			String pattern = patternString + "*"; //$NON-NLS-1$
			if (includeLeadingWildcard) {
				pattern = "*" + pattern; //$NON-NLS-1$
			}
			matcher = new StringMatcher(pattern, true, false);
		}
	}

		cache.clear();
		foundAnyCache.clear();
	}

	private boolean match(String string) {
		if (matcher == null) {
			return true;
		}
		return matcher.match(string);
	}

	public boolean isElementSelectable(Object element) {
		return element != null;
	}

	public boolean isElementVisible(Viewer viewer, Object element) {
		return isParentMatch(viewer, element) || isLeafMatch(viewer, element);
	}

	protected boolean isParentMatch(Viewer viewer, Object element) {
		Object[] children = ((ITreeContentProvider) ((AbstractTreeViewer) viewer)
				.getContentProvider()).getChildren(element);

		if ((children != null) && (children.length > 0)) {
			return isAnyVisible(viewer, element, children);
		}
		return false;
	}

	protected boolean isLeafMatch(Viewer viewer, Object element) {
		String labelText = ((ILabelProvider) ((StructuredViewer) viewer)
				.getLabelProvider()).getText(element);

		if (labelText == null) {
			return false;
		}
		return wordMatches(labelText);
	}

	private String[] getWords(String text) {
		List words = new ArrayList();
		BreakIterator iter = BreakIterator.getWordInstance();
		iter.setText(text);
		int i = iter.first();
		while (i != java.text.BreakIterator.DONE && i < text.length()) {
			int j = iter.following(i);
			if (j == java.text.BreakIterator.DONE) {
				j = text.length();
			}
			if (Character.isLetterOrDigit(text.charAt(i))) {
				String word = text.substring(i, j);
				words.add(word);
			}
			i = j;
		}
		return (String[]) words.toArray(new String[words.size()]);
	}

	protected boolean wordMatches(String text) {
		if (text == null) {
			return false;
		}

		if (match(text)) {
			return true;
		}

		String[] words = getWords(text);
		for (String word : words) {
			if (match(word)) {
				return true;
			}
		}

		return false;
	}

	void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
}

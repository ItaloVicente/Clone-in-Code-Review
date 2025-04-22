package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.dialogs.PatternFilter;
import org.eclipse.ui.internal.preferences.WorkbenchPreferenceExtensionNode;

public class PreferencePatternFilter extends PatternFilter {

	private Map keywordCache = new HashMap();

	public PreferencePatternFilter() {
		super();
	}

	private String[] getKeywords(Object element) {
		List keywordList = new ArrayList();
		if (element instanceof WorkbenchPreferenceExtensionNode) {
			WorkbenchPreferenceExtensionNode workbenchNode = (WorkbenchPreferenceExtensionNode) element;

			Collection keywordCollection = (Collection) keywordCache
					.get(element);
			if (keywordCollection == null) {
				keywordCollection = workbenchNode.getKeywordLabels();
				keywordCache.put(element, keywordCollection);
			}
			if (!keywordCollection.isEmpty()){
				Iterator keywords = keywordCollection.iterator();
				while (keywords.hasNext()) {
					keywordList.add(keywords.next());
				}
			}
		}
		return (String[]) keywordList.toArray(new String[keywordList.size()]);
	}

	@Override
	public boolean isElementSelectable(Object element) {
		return element instanceof WorkbenchPreferenceExtensionNode;
	}

	@Override
	public boolean isElementVisible(Viewer viewer, Object element) {
	    if (WorkbenchActivityHelper.restrictUseOf(
	            element))
	        return false;
	    
		if (isLeafMatch(viewer, element)) {
			return true;
		}

		ITreeContentProvider contentProvider = (ITreeContentProvider) ((TreeViewer) viewer)
				.getContentProvider();
		IPreferenceNode node = (IPreferenceNode) element;
		Object[] children = contentProvider.getChildren(node);
		if (filter(viewer, element, children).length > 0) {
			return true;
		}		
		return false;
	}

	@Override
	protected boolean isLeafMatch(Viewer viewer, Object element) {
		IPreferenceNode node = (IPreferenceNode) element;
		String text = node.getLabelText();

		if (wordMatches(text)) {
			return true;
		}

		String[] keywords = getKeywords(node);
		for (int i = 0; i < keywords.length; i++){
			if (wordMatches(keywords[i])) {
				return true;
			}
		}
		return false;
	}

}

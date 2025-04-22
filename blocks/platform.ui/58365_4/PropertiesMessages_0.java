package org.eclipse.ui.internal.views.contentoutline.actions;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.ui.dialogs.filteredtree.FilteredTree;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class ToggleTreeFilterAction extends Action {

    public static final String TOGGLE_FILTER_TREE_ACTION_ID = "toggleFilterTreeActionId"; //$NON-NLS-1$

	private FilteredTree filteredTree;

    public ToggleTreeFilterAction(FilteredTree filteredTree) {
        this.filteredTree = filteredTree;
        Bundle bundle = FrameworkUtil.getBundle(getClass());
        URL filterIconUrl = FileLocator.find(bundle, new Path("icons/full/elcl16/filter_ps.png"), null); //$NON-NLS-1$
        setImageDescriptor(ImageDescriptor.createFromURL(filterIconUrl));
        setToolTipText(PropertiesMessages.ShowFilterTextToggle_toolTip);
        setId(TOGGLE_FILTER_TREE_ACTION_ID);
    }

    @Override
    public void run() {
        if (filteredTree != null || !filteredTree.isDisposed()) {
            filteredTree.setShowFilterControls(isChecked());
        }
    }

	public FilteredTree getFilteredTree() {
        return filteredTree;
    }

    public void setFilteredTree(FilteredTree filteredTree) {
        this.filteredTree = filteredTree;
    }
}

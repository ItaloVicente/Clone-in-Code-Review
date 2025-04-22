package org.eclipse.ui.tests.views.properties.tabbed.override.folders;

import org.eclipse.ui.tests.views.properties.tabbed.model.Element;
import org.eclipse.ui.tests.views.properties.tabbed.model.File;
import org.eclipse.ui.tests.views.properties.tabbed.model.Folder;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.FileItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.FolderItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;

public class AdvancedTabFolder extends AbstractTabFolder {

	public boolean appliesTo(Element element) {
		return ((element instanceof File) || (element instanceof Folder));
	}

	public IOverrideTestsItem[] getItem() {
		return new IOverrideTestsItem[] { new FileItem(), new FolderItem() };
	}

}

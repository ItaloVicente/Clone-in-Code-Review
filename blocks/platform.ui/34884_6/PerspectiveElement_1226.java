
package org.eclipse.ui.internal.quickaccess;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;

public class EditorProvider extends QuickAccessProvider {

	private Map idToElement;

	@Override
	public QuickAccessElement getElementForId(String id) {
		getElements();
		return (EditorElement) idToElement.get(id);
	}

	@Override
	public QuickAccessElement[] getElements() {
		if (idToElement == null) {
			idToElement = new HashMap();
			IWorkbenchPage activePage = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();
			if (activePage == null) {
				return new QuickAccessElement[0];
			}
			IEditorReference[] editors = activePage.getEditorReferences();
			for (int i = 0; i < editors.length; i++) {
				EditorElement editorElement = new EditorElement(editors[i],
						this);
				idToElement.put(editorElement.getId(), editorElement);
			}
		}
		return (QuickAccessElement[]) idToElement.values().toArray(
				new QuickAccessElement[idToElement.values().size()]);
	}

	@Override
	public String getId() {
		return "org.eclipse.ui.editors"; //$NON-NLS-1$
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
	}

	@Override
	public String getName() {
		return QuickAccessMessages.QuickAccess_Editors;
	}

	@Override
	protected void doReset() {
		idToElement = null;
	}
}

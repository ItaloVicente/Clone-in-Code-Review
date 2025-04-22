
package org.eclipse.ui.internal.keys.model;

public class CommonModel extends ModelElement {

	public static final String PROP_SELECTED_ELEMENT = "selectedElement"; //$NON-NLS-1$
	private ModelElement selectedElement;

	public CommonModel(KeyController kc) {
		super(kc);
	}

	public ModelElement getSelectedElement() {
		return selectedElement;
	}

	public void setSelectedElement(ModelElement selectedContext) {
		ModelElement old = this.selectedElement;
		this.selectedElement = selectedContext;
		controller.firePropertyChange(this, PROP_SELECTED_ELEMENT, old,
				selectedContext);
	}

}


package org.eclipse.ui.internal.keys.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.bindings.BindingManager;
import org.eclipse.jface.bindings.Scheme;

public class SchemeModel extends CommonModel {

	public static final String PROP_SCHEMES = "schemes"; //$NON-NLS-1$
	private List schemes;

	public SchemeModel(KeyController kc) {
		super(kc);
	}

	public void init(BindingManager bindingManager) {
		schemes = new ArrayList();
		Scheme[] definedSchemes = bindingManager.getDefinedSchemes();
		for (int i = 0; i < definedSchemes.length; i++) {
			SchemeElement se = new SchemeElement(controller);
			se.init(definedSchemes[i]);
			se.setParent(this);
			schemes.add(se);
			if (definedSchemes[i] == bindingManager.getActiveScheme()) {
				setSelectedElement(se);
			}
		}
	}

	public List getSchemes() {
		return schemes;
	}

	public void setSchemes(List schemes) {
		List old = this.schemes;
		this.schemes = schemes;
		controller.firePropertyChange(this, PROP_SCHEMES, old, schemes);
	}

}

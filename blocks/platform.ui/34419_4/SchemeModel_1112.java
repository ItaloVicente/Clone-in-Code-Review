
package org.eclipse.ui.internal.keys.model;

import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.bindings.Scheme;

public class SchemeElement extends ModelElement {

	public SchemeElement(KeyController kc) {
		super(kc);
	}

	public void init(Scheme scheme) {
		setId(scheme.getId());
		setModelObject(scheme);
		try {
			setName(scheme.getName());
			setDescription(scheme.getDescription());
		} catch (NotDefinedException e) {
			e.printStackTrace();
		}
	}
}

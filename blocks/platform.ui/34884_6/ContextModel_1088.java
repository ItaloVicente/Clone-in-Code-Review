
package org.eclipse.ui.internal.keys.model;

import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.commands.contexts.Context;

public class ContextElement extends ModelElement {
	
	public ContextElement(KeyController kc) {
		super(kc);
	}
	
	public void init(Context context) {
		setId(context.getId());
		setModelObject(context);
		try {
			setName(context.getName());
			setDescription(context.getDescription());
		} catch (NotDefinedException e) {
		}
	}
}

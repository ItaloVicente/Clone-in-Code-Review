
package org.eclipse.e4.ui.internal.workbench;

import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;

public class E4XMISave extends XMISaveImpl {

	public E4XMISave(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected void saveElement(InternalEObject o, EStructuralFeature f) {
		if (o instanceof MApplicationElement) {
			MApplicationElement appElement = (MApplicationElement) o;
			String persists = appElement.getPersistedState().get(IWorkbench.PERSIST_STATE);
			if (persists != null && !Boolean.parseBoolean(persists)) {
				return;
			}
		}

		super.saveElement(o, f);
	}
}

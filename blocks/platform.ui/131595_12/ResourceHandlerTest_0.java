
package org.eclipse.e4.ui.internal.workbench;

import java.util.Map;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;

public class E4XMISave extends XMISaveImpl {

	public E4XMISave(Map<?, ?> options, XMLHelper helper, String encoding, String xmlVersion) {
		super(options, helper, encoding, xmlVersion);
	}

	public E4XMISave(Map<?, ?> options, XMLHelper helper, String encoding) {
		super(options, helper, encoding);
	}

	public E4XMISave(XMLHelper helper) {
		super(helper);
	}

	@Override
	protected void saveElement(InternalEObject o, EStructuralFeature f) {
		if (o instanceof MApplicationElement) {
			Object persistedState = ((MApplicationElement) o).getTransientData().get(IWorkbench.PERSIST_STATE);
			if (Boolean.FALSE.equals(persistedState))
				return;
		}

		super.saveElement(o, f);
	}
}

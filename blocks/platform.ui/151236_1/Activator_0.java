package org.eclipse.e4.emf.internal.xpath;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

public class EDynamicPropertyHandler implements DynamicPropertyHandler {
	@Override
	public String[] getPropertyNames(Object object) {
		Assert.isLegal(object instanceof EObject);
		EClass eClass = ((EObject) object).eClass();
		String[] propNames = eClass.getEStructuralFeatures().stream().map(f -> f.getName()).toArray(String[]::new);
		return propNames;
	}

	@Override
	public Object getProperty(Object object, String propertyName) {
		Assert.isLegal(object instanceof EObject);
		EClass eClass = ((EObject) object).eClass();
		return eClass.eGet(eClass.getEStructuralFeature(propertyName));
	}

	@Override
	public void setProperty(Object object, String propertyName, Object value) {
		Assert.isLegal(object instanceof EObject);
		EClass eClass = ((EObject) object).eClass();
		eClass.eSet(eClass.getEStructuralFeature(propertyName), value);
	}
}

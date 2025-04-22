package org.eclipse.e4.emf.internal.xpath.helper;

import org.apache.commons.jxpath.DynamicPropertyHandler;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EDynamicPropertyHandler implements DynamicPropertyHandler {
	@Override
	public String[] getPropertyNames(Object object) {
		Assert.isLegal(object instanceof EObject);
		EClass eClass = ((EObject) object).eClass();
		String[] propNames = eClass.getEAllStructuralFeatures().stream().map(f -> f.getName()).toArray(String[]::new);
		return propNames;
	}

	@Override
	public Object getProperty(Object object, String propertyName) {
		Assert.isLegal(object instanceof EObject);
		EObject eObject = (EObject) object;
		EStructuralFeature feature = eObject.eClass().getEStructuralFeature(propertyName);
		return feature != null ? eObject.eGet(feature) : null;
	}

	@Override
	public void setProperty(Object object, String propertyName, Object value) {
		Assert.isLegal(object instanceof EObject);
		EObject eObject = (EObject) object;
		EStructuralFeature feature = eObject.eClass().getEStructuralFeature(propertyName);
		if (feature != null) {
			eObject.eSet(feature, value);
		}
	}
}

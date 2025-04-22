package org.eclipse.e4.emf.xpath;

import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.eclipse.e4.emf.internal.xpath.EObjectPointerFactory;
import org.eclipse.e4.emf.internal.xpath.JXPathContextFactoryImpl;
import org.eclipse.emf.ecore.EObject;

public class EcoreXPathContextFactory{

	static {
		JXPathContextReferenceImpl.addNodePointerFactory(new EObjectPointerFactory());
	}

	public static XPathContextFactory<EObject> newInstance() {
		return new JXPathContextFactoryImpl<>();
	}

}

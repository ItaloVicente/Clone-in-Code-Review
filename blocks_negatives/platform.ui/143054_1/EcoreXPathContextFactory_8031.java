package org.eclipse.e4.emf.xpath;

import org.apache.commons.jxpath.ri.JXPathContextReferenceImpl;
import org.eclipse.e4.emf.internal.xpath.EObjectPointerFactory;
import org.eclipse.e4.emf.internal.xpath.JXPathContextFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * Factory which creates an XPathContextFactory for {@link EObject}s
 *
 * @since 1.0
 */
public class EcoreXPathContextFactory{

	static {
		JXPathContextReferenceImpl.addNodePointerFactory(new EObjectPointerFactory());
	}

	/**
	 * Create a new factory
	 *
	 * @return the factory
	 */
	public static XPathContextFactory<EObject> newInstance() {
		return new JXPathContextFactoryImpl<>();
	}

}

package org.eclipse.e4.emf.internal.xpath;

import org.eclipse.e4.emf.xpath.XPathContext;
import org.eclipse.e4.emf.xpath.XPathContextFactory;


/**
 * Factory creating context using JXPath
 *
 * @param <Type>
 *            the object the XPath is created for
 */
public class JXPathContextFactoryImpl<Type> extends XPathContextFactory<Type> {

	@Override
	public XPathContext newContext(XPathContext parentContext, Object contextBean) {
		return new JXPathContextImpl(parentContext, contextBean);
	}

	@Override
	public XPathContext newContext(Type contextBean) {
		return new JXPathContextImpl(contextBean);
	}

}

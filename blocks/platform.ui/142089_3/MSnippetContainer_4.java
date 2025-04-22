package org.eclipse.e4.emf.xpath;

import org.eclipse.e4.emf.internal.xpath.JXPathContextFactoryImpl;

public abstract class XPathContextFactory<Type extends Object> {

	public abstract XPathContext newContext(Type contextBean);

	public abstract XPathContext newContext(XPathContext parentContext, Type contextBean);

	public static <Type> XPathContextFactory<Type> newInstance() {
		return new JXPathContextFactoryImpl<>();
	}
}

package org.eclipse.e4.emf.xpath;

import java.util.Iterator;

public interface XPathContext {

	Object getValue(String xpath);

	Object getValue(String xpath, Class<?> requiredType);

	<O> Iterator<O> iterate(String xpath);
}

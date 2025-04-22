
package org.eclipse.ui.services;

public interface IServiceLocator {


	public <T> T getService(Class<T> api);

	public boolean hasService(Class<?> api);
}

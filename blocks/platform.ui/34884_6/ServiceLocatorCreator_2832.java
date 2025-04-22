
package org.eclipse.ui.internal.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.ui.services.AbstractServiceFactory;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.ui.services.IServiceLocator;

public final class ServiceLocator implements IDisposable, INestable,
		IServiceLocator {
	boolean activated = false;

	private static class ParentLocator implements IServiceLocator {
		private IServiceLocator locator;
		private Class key;

		public ParentLocator(IServiceLocator parent, Class serviceInterface) {
			locator = parent;
			key = serviceInterface;
		}

		@Override
		public Object getService(Class api) {
			if (key.equals(api)) {
				return locator.getService(key);
			}
			return null;
		}

		@Override
		public boolean hasService(Class api) {
			if (key.equals(api)) {
				return true;
			}
			return false;
		}
	}

	private AbstractServiceFactory factory;

	private IServiceLocator parent;

	private boolean disposed;

	private final IDisposable owner;

	private IEclipseContext e4Context;

	private Map<Class<?>, Object> servicesToDispose = new HashMap<Class<?>, Object>();

	public ServiceLocator() {
		this(null, null, null);
	}

	public ServiceLocator(final IServiceLocator parent,
			AbstractServiceFactory factory, IDisposable owner) {
		this.parent = parent;
		this.factory = factory;
		this.owner = owner;
	}

	@Override
	public final void activate() {
		activated = true;

		for (Object service : servicesToDispose.values()) {
			if (service instanceof INestable) {
				((INestable) service).activate();
			}
		}
	}

	@Override
	public final void deactivate() {
		activated = false;

		for (Object service : servicesToDispose.values()) {
			if (service instanceof INestable) {
				((INestable) service).deactivate();
			}
		}
	}

	@Override
	public final void dispose() {
		Iterator<Object> i = servicesToDispose.values().iterator();
		while (i.hasNext()) {
			Object obj = i.next();
			if (obj instanceof IDisposable) {
				((IDisposable) obj).dispose();
			}
		}
		servicesToDispose.clear();
		e4Context = null;
		disposed = true;
	}

	@Override
	public final Object getService(final Class key) {
		if (disposed) {
			return null;
		} else if (IEclipseContext.class.equals(key)) {
			return e4Context;
		}

		Object service = e4Context.get(key.getName());
		if (service == null) {
			service = servicesToDispose.get(key);
		} else if (service == e4Context.getLocal(key.getName())) {
			registerService(key, service, false);
		}

		if (service == null) {
			IServiceLocator factoryParent = WorkbenchServiceRegistry.GLOBAL_PARENT;
			if (parent != null) {
				factoryParent = new ParentLocator(parent, key);
			}
			if (factory != null) {
				service = factory.create(key, factoryParent, this);
			}
			if (service == null) {
				service = WorkbenchServiceRegistry.getRegistry().getService(key, factoryParent,
						this);
			}
			if (service == null) {
				service = factoryParent.getService(key);
			} else {
				registerService(key, service, true);
			}
		}
		return service;
	}

	@Override
	public final boolean hasService(final Class key) {
		if (disposed) {
			return false;
		}
		return e4Context.containsKey(key.getName());
	}

	public final void registerService(final Class api, final Object service) {
		registerService(api, service, true);
	}

	private void registerService(Class<?> api, Object service, boolean saveInContext) {
		if (api == null) {
			throw new NullPointerException("The service key cannot be null"); //$NON-NLS-1$
		}

		if (!api.isInstance(service)) {
			throw new IllegalArgumentException(
					"The service does not implement the given interface"); //$NON-NLS-1$
		}

		if (service instanceof INestable && activated) {
			((INestable) service).activate();
		}

		servicesToDispose.put(api, service);

		if (saveInContext) {
			e4Context.set(api.getName(), service);
		}
	}

	public boolean isDisposed() {
		return disposed;
	}

	public void unregisterServices(String[] serviceNames) {
		if (owner != null) {
			owner.dispose();
		}
	}

	public void setContext(IEclipseContext context) {
		e4Context = context;
	}

	public IEclipseContext getContext() {
		return e4Context;
	}
}


package org.eclipse.ui.statushandlers;

import java.util.HashMap;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;

public class StatusAdapter implements IAdaptable {

	@Deprecated
	public static final QualifiedName TITLE_PROPERTY = IStatusAdapterConstants.TITLE_PROPERTY;

	@Deprecated
	public static final QualifiedName TIMESTAMP_PROPERTY = IStatusAdapterConstants.TIMESTAMP_PROPERTY;

	private IStatus status;

	private HashMap properties;

	private HashMap adapters;

	public StatusAdapter(IStatus status) {
		this.status = status;
	}

	public void addAdapter(Class adapter, Object object) {
		if (adapters == null) {
			adapters = new HashMap();
		}
		adapters.put(adapter, object);
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (adapters == null) {
			return null;
		}
		return adapters.get(adapter);
	}

	public IStatus getStatus() {
		return status;
	}

	public void setStatus(IStatus status) {
		this.status = status;
	}

	public Object getProperty(QualifiedName key) {
		if (properties == null) {
			return null;
		}
		return properties.get(key);
	}

	public void setProperty(QualifiedName key, Object value) {
		if (properties == null) {
			properties = new HashMap();
		}
		properties.put(key, value);
	}
}

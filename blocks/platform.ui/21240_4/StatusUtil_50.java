
package org.eclipse.e4.ui.progress.legacy;

import java.util.HashMap;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;

public class StatusAdapter implements IAdaptable {

	static final String PROPERTY_PREFIX = "org.eclipse.e4.ui.workbench.progress"
			+ ".workbench.statusHandlers.adapters"; //$NON-NLS-1$

	public static final QualifiedName TITLE_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "title"); //$NON-NLS-1$

	public static final QualifiedName TIMESTAMP_PROPERTY = new QualifiedName(
			PROPERTY_PREFIX, "timestamp"); //$NON-NLS-1$

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

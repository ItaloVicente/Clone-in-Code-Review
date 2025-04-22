
package org.eclipse.ui.internal.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.views.IViewCategory;
import org.eclipse.ui.views.IViewDescriptor;

public class ViewCategory implements IViewCategory {

	private String id;
	private String label;
	private IPath path;
	private List<IViewDescriptor> descriptors = new ArrayList<IViewDescriptor>();

	public ViewCategory(String id, String label) {
		this.id = id;
		this.label = label;
		this.path = new Path(id);
	}

	void addDescriptor(IViewDescriptor descriptor) {
		descriptors.add(descriptor);
	}

	@Override
	public IViewDescriptor[] getViews() {
		Collection<?> allowedViews = WorkbenchActivityHelper.restrictCollection(descriptors,
				new ArrayList<Object>());
		return allowedViews.toArray(new IViewDescriptor[allowedViews.size()]);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public IPath getPath() {
		return path;
	}

}

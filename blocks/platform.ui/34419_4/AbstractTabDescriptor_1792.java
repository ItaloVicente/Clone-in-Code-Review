
package org.eclipse.ui.views.properties.tabbed;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.views.properties.tabbed.TabbedPropertyViewPlugin;

public abstract class AbstractTabDescriptor implements ITabDescriptor,
		Cloneable {

	private List sectionDescriptors;

	public AbstractTabDescriptor() {
		super();
		sectionDescriptors = new ArrayList(5);
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException exception) {
			IStatus status = new Status(IStatus.ERROR, TabbedPropertyViewPlugin
					.getPlugin().getBundle().getSymbolicName(), 666, exception
					.getMessage(), exception);
			TabbedPropertyViewPlugin.getPlugin().getLog().log(status);
		}
		return null;
	}

	public TabContents createTab() {
		List sections = new ArrayList(getSectionDescriptors().size());
		for (Iterator iter = getSectionDescriptors().iterator(); iter.hasNext();) {
			ISectionDescriptor descriptor = (ISectionDescriptor) iter.next();
			ISection section = descriptor.getSectionClass();
			sections.add(section);
		}
		TabContents tab = new TabContents();
		tab.setSections((ISection[]) sections.toArray(new ISection[sections
				.size()]));
		return tab;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (this.getClass() == object.getClass()) {
			AbstractTabDescriptor descriptor = (AbstractTabDescriptor) object;
			if (this.getCategory().equals(descriptor.getCategory()) &&
					this.getId().equals(descriptor.getId()) &&
					this.getSectionDescriptors().size() == descriptor
							.getSectionDescriptors().size()) {

				Iterator i = this.getSectionDescriptors().iterator();
				Iterator j = descriptor.getSectionDescriptors().iterator();

				while (i.hasNext()) {
					ISectionDescriptor source = (ISectionDescriptor) i.next();
					ISectionDescriptor target = (ISectionDescriptor) j.next();
					if (!source.getId().equals(target.getId())) {
						return false;
					}
				}

				return true;
			}

		}

		return false;
	}

	public String getAfterTab() {
		return TOP;
	}

	public Image getImage() {
		return null;
	}

	public List getSectionDescriptors() {
		return sectionDescriptors;
	}

	public String getText() {
		return getLabel();
	}

	public int hashCode() {

		int hashCode = getCategory().hashCode();
		hashCode ^= getId().hashCode();
		Iterator i = this.getSectionDescriptors().iterator();
		while (i.hasNext()) {
			ISectionDescriptor section = (ISectionDescriptor) i.next();
			hashCode ^= section.getId().hashCode();
		}
		return hashCode;
	}

	public boolean isIndented() {
		return false;
	}

	public boolean isSelected() {
		return false;
	}

	public void setSectionDescriptors(List sectionDescriptors) {
		this.sectionDescriptors = sectionDescriptors;
	}
}

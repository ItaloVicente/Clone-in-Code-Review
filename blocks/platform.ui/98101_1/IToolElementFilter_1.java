
package org.eclipse.e4.ui.workbench.filter;

import org.eclipse.e4.ui.model.application.descriptor.basic.MPartDescriptor;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

public interface IPartFilter {

	public boolean filterPart(MPart part);

	public boolean filterPart(MPartDescriptor partDescriptor);

}


package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.resource.DeviceResourceException;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.ide.IDEInternalWorkbenchImages;

public class FieldSeverityAndMessage extends FieldMessage {

	private String description;

	public FieldSeverityAndMessage() {
		description = MarkerMessages.problemSeverity_description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Image getImage(Object obj) {
		if (obj == null || !(obj instanceof MarkerNode)) {
			return null;
		}

		MarkerNode node = (MarkerNode) obj;
		if (node.isConcrete()) {
			if (node instanceof ProblemMarker) {
				return Util.getImage(((ProblemMarker) obj).getSeverity());
			}
			return null;
		}

		try {
			return JFaceResources
					.getResources()
					.createImageWithDefault(
							IDEInternalWorkbenchImages
									.getImageDescriptor(IDEInternalWorkbenchImages.IMG_ETOOL_PROBLEM_CATEGORY));
		} catch (DeviceResourceException e) {
			return null;
		}
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null || !(obj1 instanceof ProblemMarker)
				|| !(obj2 instanceof ProblemMarker)) {
			return 0;
		}
		
		ProblemMarker marker1 = (ProblemMarker) obj1;
		ProblemMarker marker2 = (ProblemMarker) obj2;

		int severity1 = marker1.getSeverity();
		int severity2 = marker2.getSeverity();
		if(severity1 == severity2)
			return marker1.getDescriptionKey().compareTo(
					marker2.getDescriptionKey());
		return severity2 - severity1;
	}
	
	@Override
	public Image getColumnHeaderImage() {
		return getImage(FieldDone.DESCRIPTION_IMAGE_PATH);
	}


}

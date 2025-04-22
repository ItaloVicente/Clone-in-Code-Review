
package org.eclipse.ui.views.markers.internal;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

public class FieldDone extends AbstractField {

	static final String DESCRIPTION_IMAGE_PATH = "obj16/header_complete.png"; //$NON-NLS-1$

	static final String COMPLETE_IMAGE_PATH = "obj16/complete_tsk.png"; //$NON-NLS-1$

	static final String INCOMPLETE_IMAGE_PATH = "obj16/incomplete_tsk.png"; //$NON-NLS-1$

	private String description = MarkerMessages.completion_description;


	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public Image getDescriptionImage() {
		return getImage(DESCRIPTION_IMAGE_PATH);
	}
	
	private Image getImage(String path){
		return JFaceResources.getResources().createImageWithDefault(
				IDEWorkbenchPlugin
						.getIDEImageDescriptor(path));
		
	}

	@Override
	public String getColumnHeaderText() {
		return ""; //$NON-NLS-1$
	}

	@Override
	public Image getColumnHeaderImage() {
		return getImage(DESCRIPTION_IMAGE_PATH);
	}

	@Override
	public String getValue(Object obj) {
		return ""; //$NON-NLS-1$
	}

	@Override
	public Image getImage(Object obj) {
		if (obj == null || !(obj instanceof TaskMarker)) {
			return null;
		}
		TaskMarker marker = (TaskMarker) obj;
		int done = marker.getDone();
		if (done == -1) {
			return null;
		}
		if (done == 1) {
			return getImage(COMPLETE_IMAGE_PATH);
		}
		return getImage(INCOMPLETE_IMAGE_PATH);
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null || !(obj1 instanceof TaskMarker)
				|| !(obj2 instanceof TaskMarker)) {
			return 0;
		}
		TaskMarker marker1 = (TaskMarker) obj1;
		TaskMarker marker2 = (TaskMarker) obj2;
		int value1 = marker1.getDone();
		int value2 = marker2.getDone();
		return value1 - value2;
	}
	
	@Override
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}
	
	@Override
	public int getPreferredWidth() {
		return 40;
	}

}

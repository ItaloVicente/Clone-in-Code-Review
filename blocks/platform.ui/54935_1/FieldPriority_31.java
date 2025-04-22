
package org.eclipse.ui.views.markers.internal;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

public class FieldMessage extends AbstractField {

	public FieldMessage() {
	}

	@Override
	public String getDescription() {
		return MarkerMessages.description_message;
	}

	@Override
	public Image getDescriptionImage() {
		return null;
	}

	@Override
	public String getColumnHeaderText() {
		return MarkerMessages.description_message;
	}

	@Override
	public Image getColumnHeaderImage() {
		return null;
	}

	@Override
	public String getValue(Object obj) {
		if (obj == null) {
			return MarkerMessages.FieldMessage_NullMessage;
		}

		if (obj instanceof MarkerNode) {
			return ((MarkerNode) obj).getDescription();
		}
		return NLS.bind(MarkerMessages.FieldMessage_WrongType, obj.toString());
	}

	@Override
	public Image getImage(Object obj) {
		return null;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null || !(obj1 instanceof ConcreteMarker)
				|| !(obj2 instanceof ConcreteMarker)) {
			return 0;
		}

		ConcreteMarker marker1 = (ConcreteMarker) obj1;
		ConcreteMarker marker2 = (ConcreteMarker) obj2;

		return marker1.getDescriptionKey().compareTo(
				marker2.getDescriptionKey());
	}

	@Override
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}

	@Override
	public int getPreferredWidth() {
		return 250;
	}
}

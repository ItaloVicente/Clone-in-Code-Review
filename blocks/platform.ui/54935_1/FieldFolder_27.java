package org.eclipse.ui.views.markers.internal;


import org.eclipse.swt.graphics.Image;

public class FieldDummy extends AbstractField implements IField {

	@Override
	public String getDescription() {
		return Util.EMPTY_STRING;
	}

	@Override
	public Image getDescriptionImage() {
		return null;
	}

	@Override
	public String getColumnHeaderText() {
		return Util.EMPTY_STRING;
	}

	@Override
	public Image getColumnHeaderImage() {
		return null;
	}

	@Override
	public String getValue(Object obj) {
		return Util.EMPTY_STRING;
	}

	@Override
	public Image getImage(Object obj) {
		return null;
	}

	@Override
	public int compare(Object obj1, Object obj2) {
		return 0;
	}

	@Override
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}

	@Override
	public int getPreferredWidth() {
		return 0;
	}

}

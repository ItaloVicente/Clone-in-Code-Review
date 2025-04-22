
package org.eclipse.e4.ui.internal.workbench;

import java.util.Comparator;
import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.fragment.MStringModelFragment;
import org.eclipse.e4.ui.model.internal.ModelUtils;
import org.eclipse.e4.ui.model.internal.PositionInfo;

public class ModelFragmentComparator implements Comparator<ModelFragmentWrapper> {

	@Override
	public int compare(ModelFragmentWrapper o1, ModelFragmentWrapper o2) {
		if (o1 == o2)
			return 0;

		PositionDescription posInfo1 = getPositionDescription(o1);
		PositionDescription posInfo2 = getPositionDescription(o2);

		switch (posInfo1.getPlace()) {
		case NONE:
			switch (posInfo2.getPlace()) {
			case INDEX:
			case NONE:
				return 1;
			default:
				return -1;
			}
		case ABSOLUTE:
			switch (posInfo2.getPlace()) {
			case RELATIVE:
				return -1;
			default:
				return 1;
			}
		case INDEX:
			switch (posInfo2.getPlace()) {
			case INDEX:
				return posInfo1.getPositionReferenceAsInteger() - posInfo2.getPositionReferenceAsInteger();
			default:
				return -1;
			}
		case RELATIVE:
			switch (posInfo2.getPlace()) {
			case RELATIVE:
				boolean hasElement = false;
				for (MApplicationElement element : o2.getModelFragment().getElements()) {
					hasElement |= ModelUtils.findElementById(element, posInfo1.getReference()) != null;
					if (hasElement)
						break;
				}
				if (hasElement)
					return 1;
				hasElement = false;
				for (MApplicationElement element : o1.getModelFragment().getElements()) {
					hasElement |= ModelUtils.findElementById(element, posInfo2.getReference()) != null;
					if (hasElement)
						break;
				}
				if (hasElement)
					return -1;
				return -1;
			default:
				return 1;
			}
		default:
			return 1;
		}
	}

	private PositionDescription getPositionDescription(ModelFragmentWrapper wrapper) {
		if (!MStringModelFragment.class.isInstance(wrapper.getModelFragment()))
			return new PositionDescription(PositionPlace.NONE, null);
		MStringModelFragment stringFragment = (MStringModelFragment) wrapper.getModelFragment();
		if (stringFragment.getPositionInList() == null)
			return new PositionDescription(PositionPlace.NONE, null);
		String posInList = stringFragment.getPositionInList().trim();
		PositionInfo posInfo = PositionInfo.parse(posInList);
		if (posInfo == null)
			return new PositionDescription(PositionPlace.NONE, null);
		switch (posInfo.getPosition()) {
		case AFTER:
		case BEFORE:
			return new PositionDescription(PositionPlace.RELATIVE, posInfo.getPositionReference());
		case FIRST:
		case LAST:
			return new PositionDescription(PositionPlace.ABSOLUTE, posInfo.getPositionReference());
		case INDEX:
			return new PositionDescription(PositionPlace.INDEX, posInfo.getPositionReference());
		default:
			return new PositionDescription(PositionPlace.NONE, null);
		}
	}

	private class PositionDescription {
		private final PositionPlace place;
		private final String reference;

		PositionDescription(PositionPlace place, String reference) {
			this.place = place;
			this.reference = reference;
		}

		public PositionPlace getPlace() {
			return place;
		}

		public String getReference() {
			return reference;
		}

		public int getPositionReferenceAsInteger() {
			return Integer.parseInt(reference);
		}

	}

	private enum PositionPlace {
		NONE, ABSOLUTE, INDEX, RELATIVE
	}
}

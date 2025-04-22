package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties.sections;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.Player;

public abstract class AbstractPlayersPropertySection
	extends AbstractTablePropertySection {

	protected List getColumnLabelText() {
		ArrayList ret = new ArrayList();
		ret.add("Name");//$NON-NLS-1$
		ret.add("Jersey Number");//$NON-NLS-1$
		ret.add("Shot");//$NON-NLS-1$
		ret.addAll(getAdditionalColumnLabelText());
		return ret;
	}

	protected String getKeyForRow(Object object) {
		return ((Player) object).getName();
	}

	protected List getValuesForRow(Object object) {
		ArrayList ret = new ArrayList();
		ret.add(((Player) object).getName());
		ret.add(Integer.toString(((Player) object).getNumber()));
		ret.add(((Player) object).getShot().getName());
		ret.addAll(getAdditionalValuesForRow(object));
		return ret;
	}

	protected abstract List getAdditionalColumnLabelText();
	
	protected abstract List getAdditionalValuesForRow(Object object);

}

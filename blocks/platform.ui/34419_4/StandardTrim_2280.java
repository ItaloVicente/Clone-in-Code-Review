package org.eclipse.ui.internal;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.commands.IParameterValues;

public class SplitValues implements IParameterValues {

	private HashMap<String, String> values = new HashMap<String, String>();
	
	public SplitValues() {
		values.put(WorkbenchMessages.SplitValues_Horizontal, "true"); //$NON-NLS-1$
		values.put(WorkbenchMessages.SplitValues_Vertical, "false"); //$NON-NLS-1$
	}

	@Override
	public Map getParameterValues() {
		return values;
	}

}

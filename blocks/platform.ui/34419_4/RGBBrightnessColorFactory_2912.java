
package org.eclipse.ui.internal.themes;

import java.util.Hashtable;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.themes.ColorUtil;
import org.eclipse.ui.themes.IColorFactory;

public class LightColorFactory implements IColorFactory,
		IExecutableExtension {

	protected static final RGB white = ColorUtil.getColorValue("COLOR_WHITE"); //$NON-NLS-1$
	protected static final RGB black = ColorUtil.getColorValue("COLOR_BLACK"); //$NON-NLS-1$
	
	String baseColorName;
	String definitionId; 

	
	public static RGB createHighlightStartColor(RGB tabStartColor) {
		return ColorUtil.blend(white, tabStartColor);
	}

	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) {

		if (data instanceof Hashtable) {
			Hashtable table = (Hashtable) data;
			baseColorName = (String) table.get("base"); //$NON-NLS-1$
			definitionId = (String) table.get("definitionId"); //$NON-NLS-1$
		}
	}

	protected int valuesInRange(RGB test, int lower, int upper) {
		int hits = 0;
		if(test.red >= lower && test.red <= upper) hits++;
		if(test.blue >= lower && test.blue <= upper) hits++;
		if(test.green >= lower && test.green <= upper) hits++;

		return hits;
	}
	
	private RGB getLightenedColor(RGB sample) {
		if(valuesInRange(sample, 180, 255) >= 2)
			return sample;
		
		if(valuesInRange(sample, 100, 179) >= 2)
			return ColorUtil.blend(white, sample, 40);
		
		if(valuesInRange(sample, 0, 99) >= 2)
			return ColorUtil.blend(white, sample, 60);
		
		return ColorUtil.blend(white, sample, 30);
	}

	private RGB getActiveFocusStartColor() {
		if (Display.getCurrent().getDepth() < 15)
				return getActiveFocusEndColor();

		RGB startColor = ColorUtil.blend(white, getActiveFocusEndColor(), 75);
		return startColor;
	}

	private RGB getActiveFocusEndColor() {
		if (Display.getCurrent().getDepth() < 15)
			return ColorUtil.getColorValue(baseColorName);
	
		return getLightenedColor(
				ColorUtil.getColorValue(baseColorName));
	}	

	private RGB getActiveFocusTextColor() {
		if (Display.getCurrent().getDepth() < 15)
			return ColorUtil.getColorValue(baseColorName);  //typically TITLE_FOREGROUND
	
		return ColorUtil.getColorValue("COLOR_BLACK"); //$NON-NLS-1$
	}
	private RGB getActiveNofocusStartColor() {
		RGB base = ColorUtil.getColorValue(baseColorName);
		if (Display.getCurrent().getDepth() < 15)
			return base;
		
		return ColorUtil.blend(white, base, 40);
	}
	
	@Override
	public RGB createColor() {
		if (baseColorName == null || definitionId == null) 
			return white;

		if (definitionId.equals("org.eclipse.ui.workbench.ACTIVE_TAB_BG_START")) //$NON-NLS-1$
			return getActiveFocusStartColor();
		if (definitionId.equals("org.eclipse.ui.workbench.ACTIVE_TAB_BG_END")) //$NON-NLS-1$
			return getActiveFocusEndColor();
		if (definitionId.equals("org.eclipse.ui.workbench.ACTIVE_TAB_TEXT_COLOR")) //$NON-NLS-1$
			return getActiveFocusTextColor();
		if (definitionId.equals("org.eclipse.ui.workbench.ACTIVE_NOFOCUS_TAB_BG_START")) //$NON-NLS-1$
			return getActiveNofocusStartColor();
		
		return white;
	}
}

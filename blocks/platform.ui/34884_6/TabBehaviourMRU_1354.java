
package org.eclipse.ui.internal.tweaklets;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.internal.tweaklets.Tweaklets.TweakKey;

public abstract class TabBehaviour {

	public static TweakKey KEY = new Tweaklets.TweakKey(TabBehaviour.class);

	static {
		Tweaklets.setDefault(TabBehaviour.KEY, new TabBehaviourMRU());
	}

	public abstract boolean alwaysShowPinAction();

	public abstract IEditorReference findReusableEditor(WorkbenchPage page);

	public abstract IEditorReference reuseInternalEditor(WorkbenchPage page,
 Object manager,
			Object editorPresentation,
			EditorDescriptor desc, IEditorInput input,
			IEditorReference reusableEditorRef);

	public void setPreferenceVisibility(Composite editorReuseGroup,
			Button showMultipleEditorTabs) {
	}

	public boolean autoPinOnDirty() {
		return false;
	}

	public boolean isPerTabHistoryEnabled() {
		return false;
	}

	public int getReuseEditorMatchFlags(int originalMatchFlags) {
		return originalMatchFlags;
	}

	public int getEditorReuseThreshold() {
		IPreferenceStore store = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		return store.getInt(IPreferenceConstants.REUSE_EDITORS);
	}

	public boolean enableMRUTabVisibility() {
		return true;
	}
	
	public boolean sortEditorListAlphabetically() {
		return true;		
	}
	
	public Color createVisibleEditorsColor(Display display, RGB originalForegroundColor, RGB originalBackgroundColor) {
		return new Color(display, originalForegroundColor);
	}
	
	public Font createVisibleEditorsFont(Display display, Font originalFont) {
		FontData fontData[] = originalFont.getFontData();
		return new Font(display, fontData);
	}
	
	public Font createInvisibleEditorsFont(Display display, Font originalFont) {
        FontData fontData[] = originalFont.getFontData();
        for (int i = 0; i < fontData.length; i++) {
			fontData[i].setStyle(fontData[i].getStyle() | SWT.BOLD);
		}
        return new Font(display, fontData);
	}

}

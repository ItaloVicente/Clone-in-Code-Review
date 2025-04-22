
package org.eclipse.ui.part;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.internal.IWorkbenchThemeConstants;
import org.eclipse.ui.internal.WorkbenchWindow;
import org.eclipse.ui.themes.ITheme;

public abstract class MultiEditor extends AbstractMultiEditor {

    public static class Gradient {
        public Color fgColor;

        public Color[] bgColors;

        public int[] bgPercents;
    }

	public void updateGradient(IEditorPart editor) {
	    boolean activeEditor = editor == getSite().getPage().getActiveEditor();
	    boolean activePart = editor == getSite().getPage().getActivePart();
	
	    ITheme theme = editor.getEditorSite().getWorkbenchWindow()
	            .getWorkbench().getThemeManager().getCurrentTheme();
	    Gradient g = new Gradient();
	
	    ColorRegistry colorRegistry = theme.getColorRegistry();
	    if (activePart) {
	        g.fgColor = colorRegistry
	                .get(IWorkbenchThemeConstants.ACTIVE_TAB_TEXT_COLOR);
	        g.bgColors = new Color[2];
	        g.bgColors[0] = colorRegistry
	                .get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_START);
	        g.bgColors[1] = colorRegistry
	                .get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_END);
	    } else {
	        if (activeEditor) {
	            g.fgColor = colorRegistry
	                    .get(IWorkbenchThemeConstants.ACTIVE_TAB_TEXT_COLOR);
	            g.bgColors = new Color[2];
	            g.bgColors[0] = colorRegistry
	                    .get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_START);
	            g.bgColors[1] = colorRegistry
	                    .get(IWorkbenchThemeConstants.ACTIVE_TAB_BG_END);
	        } else {
	            g.fgColor = colorRegistry
	                    .get(IWorkbenchThemeConstants.INACTIVE_TAB_TEXT_COLOR);
	            g.bgColors = new Color[2];
	            g.bgColors[0] = colorRegistry
	                    .get(IWorkbenchThemeConstants.INACTIVE_TAB_BG_START);
	            g.bgColors[1] = colorRegistry
	                    .get(IWorkbenchThemeConstants.INACTIVE_TAB_BG_END);
	        }
	    }
	    g.bgPercents = new int[] { theme
	            .getInt(IWorkbenchThemeConstants.ACTIVE_TAB_PERCENT) };
	
	    drawGradient(editor, g);
	}

	protected abstract void drawGradient(IEditorPart innerEditor, Gradient g);
    
    public Composite createInnerPartControl(Composite parent,
            final IEditorPart e) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setLayout(new FillLayout());
        e.createPartControl(content);
        parent.addListener(SWT.Activate, new Listener() {
            @Override
			public void handleEvent(Event event) {
                if (event.type == SWT.Activate) {
					activateEditor(e);
				}
            }
        });
        return content;
    }

    @Override
	public void setFocus() {
    	super.setFocus();
        updateGradient(getActiveEditor());
    }

    @Override
	public void activateEditor(IEditorPart part) {
        IEditorPart oldEditor = getActiveEditor();
        super.activateEditor(part);
        updateGradient(oldEditor);
    }

    protected boolean getShellActivated() {
        WorkbenchWindow window = (WorkbenchWindow) getSite().getPage()
                .getWorkbenchWindow();
        return window.getShellActivated();
    }

	@Override
	public Composite getInnerEditorContainer(
			IEditorReference innerEditorReference) {
		return null;
	}

	@Override
	protected void innerEditorsCreated() {
	}

}

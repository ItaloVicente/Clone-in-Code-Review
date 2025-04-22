package org.eclipse.ui.internal;

import java.util.ArrayList;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.SubActionBars2;
import org.eclipse.ui.dnd.IDragAndDropService;

public class EditorSite extends PartSite implements IEditorSite {



    private SubActionBars ab = null;
    
	public EditorSite(MPart model, IWorkbenchPart part, IWorkbenchPartReference ref,
			IConfigurationElement element) {
		super(model, part, ref, element);
        
        initializeDefaultServices();
    }

	private void initializeDefaultServices() {
		final IDragAndDropService editorDTService = new EditorSiteDragAndDropServiceImpl();
		serviceLocator.registerService(IDragAndDropService.class, editorDTService);
		serviceLocator.registerService(IEditorPart.class, (IEditorPart) getPart());
	}
	
    @Override
	public void setActionBars(SubActionBars bars) {
        super.setActionBars(bars);
        
        if (bars instanceof IActionBars2) {
            ab = new SubActionBars2((IActionBars2)bars, this);
        } else {
            ab = new SubActionBars(bars, this);
        }
    }
    
    @Override
	public void activateActionBars(boolean forceVisibility) {
        if (ab != null) {
            ab.activate(forceVisibility);
        }
        super.activateActionBars(forceVisibility);
    }

    @Override
	public void deactivateActionBars(boolean forceHide) {
        if (ab != null) {
            ab.deactivate(forceHide);
        }
        super.deactivateActionBars(forceHide);
    }
    
    @Override
	public IEditorActionBarContributor getActionBarContributor() {
        EditorActionBars bars = (EditorActionBars) getActionBars();
        if (bars != null) {
			return bars.getEditorContributor();
		}
        
        return null;
    }

    public IEditorActionBarContributor getExtensionActionBarContributor() {
        EditorActionBars bars = (EditorActionBars) getActionBars();
        if (bars != null) {
			return bars.getExtensionContributor();
		}
        
        return null;
    }

    public IEditorPart getEditorPart() {
        return (IEditorPart) getPart();
    }

    @Override
	protected String getInitialScopeId() {
        return "org.eclipse.ui.textEditorScope"; //$NON-NLS-1$
    }
    
    @Override
	public void dispose() {
        super.dispose();
        
        if (ab != null) {
            ab.dispose();
			ab = null;
        }
    }
    
    @Override
	public final void registerContextMenu(final MenuManager menuManager,
            final ISelectionProvider selectionProvider,
            final boolean includeEditorInput) {
        registerContextMenu(getId(), menuManager, selectionProvider,
                includeEditorInput);
    }
    
    @Override
	public final void registerContextMenu(final String menuId,
            final MenuManager menuManager,
            final ISelectionProvider selectionProvider,
            final boolean includeEditorInput) {
        if (menuExtenders == null) {
            menuExtenders = new ArrayList(1);
        }
        
		PartSite.registerContextMenu(menuId, menuManager, selectionProvider, includeEditorInput,
				getPart(), getModel().getContext(), menuExtenders);
    }
}

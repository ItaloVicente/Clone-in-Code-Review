
package org.eclipse.ui.part;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.e4.compatibility.E4Util;

public abstract class AbstractMultiEditor extends EditorPart {

    private int activeEditorIndex;

    private IEditorPart innerEditors[];

	private IPartListener2 propagationListener;

    public AbstractMultiEditor() {
        super();
    }

	protected void handlePropertyChange(int propId) {
		firePropertyChange(propId);
	}

    @Override
	public void doSave(IProgressMonitor monitor) {
        for (int i = 0; i < innerEditors.length; i++) {
            IEditorPart e = innerEditors[i];
            e.doSave(monitor);
        }
    }

    @Override
	public void doSaveAs() {
    }

    @Override
	public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        init(site, (MultiEditorInput) input);
    }

    public void init(IEditorSite site, MultiEditorInput input)
            throws PartInitException {
        setInput(input);
        setSite(site);
        setPartName(input.getName());
        setTitleToolTip(input.getToolTipText());
        setupEvents();
    }

    @Override
	public boolean isDirty() {
        for (int i = 0; i < innerEditors.length; i++) {
            IEditorPart e = innerEditors[i];
            if (e.isDirty()) {
				return true;
			}
        }
        return false;
    }

    @Override
	public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
	public void setFocus() {
        innerEditors[activeEditorIndex].setFocus();
    }

    public final IEditorPart getActiveEditor() {
        return innerEditors[activeEditorIndex];
    }

    public final IEditorPart[] getInnerEditors() {
        return innerEditors;
    }

    public final void setChildren(IEditorPart[] children) {
        innerEditors = children;
        activeEditorIndex = 0;

		for (int i = 0; i < children.length; i++) {
			children[i].addPropertyListener( new IPropertyListener() {
				@Override
				public void propertyChanged(Object source, int propId) {
					handlePropertyChange(propId);
				}
			});
		}

        innerEditorsCreated();
    }

	protected abstract void innerEditorsCreated();

    public void activateEditor(IEditorPart part) {
        activeEditorIndex = getIndex(part);
		E4Util.unsupported("We need to request an activation of this part"); //$NON-NLS-1$
    }

    protected int getIndex(IEditorPart editor) {
        for (int i = 0; i < innerEditors.length; i++) {
            if (innerEditors[i] == editor) {
				return i;
			}
        }
        return -1;
    }

    private void setupEvents() {
		propagationListener = new IPartListener2() {
			@Override
			public void partActivated(IWorkbenchPartReference partRef) {
			}

			@Override
			public void partBroughtToTop(IWorkbenchPartReference partRef) {
			}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part == AbstractMultiEditor.this && innerEditors != null) {
					E4Util.unsupported("propogate the events needed"); //$NON-NLS-1$
				}
			}

			@Override
			public void partDeactivated(IWorkbenchPartReference partRef) {
			}

			@Override
			public void partOpened(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part == AbstractMultiEditor.this && innerEditors != null) {
					E4Util.unsupported("propogate the events needed"); //$NON-NLS-1$
				}
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
			}

			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
			}

			@Override
			public void partInputChanged(IWorkbenchPartReference partRef) {
			}
		};
		getSite().getPage().addPartListener(propagationListener);
    }

	@Override
	public void dispose() {
		getSite().getPage().removePartListener(propagationListener);
		super.dispose();
	}

	public abstract Composite getInnerEditorContainer(IEditorReference innerEditorReference);

}

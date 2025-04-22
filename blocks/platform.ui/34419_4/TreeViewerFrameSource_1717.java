package org.eclipse.ui.internal.navigator.framelist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.AbstractTreeViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;

public class TreeFrame extends Frame {
    private static final String TAG_SELECTION = "selection"; //$NON-NLS-1$

    private static final String TAG_EXPANDED = "expanded"; //$NON-NLS-1$

    private static final String TAG_ELEMENT = "element"; //$NON-NLS-1$

    private static final String TAG_FRAME_INPUT = "frameInput"; //$NON-NLS-1$

    private static final String TAG_FACTORY_ID = "factoryID"; //$NON-NLS-1$

    private AbstractTreeViewer viewer;

    private Object input;

    private ISelection selection;

    private Object[] expandedElements;

    public TreeFrame(AbstractTreeViewer viewer) {
        this.viewer = viewer;
    }

    public TreeFrame(AbstractTreeViewer viewer, Object input) {
        this(viewer);
        setInput(input);
        ILabelProvider provider = (ILabelProvider) viewer.getLabelProvider();
        String name = provider.getText(input);
        if(name == null) {
			name = "";//$NON-NLS-1$
		}
        setName(name);
        setToolTipText(name);
    }

    public Object[] getExpandedElements() {
        return expandedElements;
    }

    public Object getInput() {
        return input;
    }

    public ISelection getSelection() {
        return selection;
    }

    public AbstractTreeViewer getViewer() {
        return viewer;
    }

    private List<IAdaptable> restoreElements(IMemento memento) {
        IMemento[] elementMem = memento.getChildren(TAG_ELEMENT);
        List<IAdaptable> elements = new ArrayList<IAdaptable>(elementMem.length);

        for (int i = 0; i < elementMem.length; i++) {
            String factoryID = elementMem[i].getString(TAG_FACTORY_ID);
            if (factoryID != null) {
                IElementFactory factory = PlatformUI.getWorkbench()
                        .getElementFactory(factoryID);
                if (factory != null) {
					elements.add(factory.createElement(elementMem[i]));
				}
            }
        }
        return elements;
    }

    public void restoreState(IMemento memento) {
        IMemento childMem = memento.getChild(TAG_FRAME_INPUT);

        if (childMem == null) {
			return;
		}

        String factoryID = childMem.getString(TAG_FACTORY_ID);
        IAdaptable frameInput = null;
        if (factoryID != null) {
            IElementFactory factory = PlatformUI.getWorkbench()
                    .getElementFactory(factoryID);
            if (factory != null) {
				frameInput = factory.createElement(childMem);
			}
        }
        if (frameInput != null) {
            input = frameInput;
        }
        IMemento expandedMem = memento.getChild(TAG_EXPANDED);
        if (expandedMem != null) {
            List<IAdaptable> elements = restoreElements(expandedMem);
            expandedElements = elements.toArray(new Object[elements.size()]);
        } else {
            expandedElements = new Object[0];
        }
        IMemento selectionMem = memento.getChild(TAG_SELECTION);
        if (selectionMem != null) {
            List<IAdaptable> elements = restoreElements(selectionMem);
            selection = new StructuredSelection(elements);
        } else {
            selection = StructuredSelection.EMPTY;
        }
    }

    private void saveElements(Object[] elements, IMemento memento) {
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof IAdaptable) {
                IPersistableElement persistable = (IPersistableElement) ((IAdaptable) elements[i])
                        .getAdapter(IPersistableElement.class);
                if (persistable != null) {
                    IMemento elementMem = memento.createChild(TAG_ELEMENT);
                    elementMem.putString(TAG_FACTORY_ID, persistable
                            .getFactoryId());
                    persistable.saveState(elementMem);
                }
            }
        }
    }

    public void saveState(IMemento memento) {
        if (!(input instanceof IAdaptable)) {
			return;
		}

        IPersistableElement persistable = (IPersistableElement) ((IAdaptable) input)
                .getAdapter(IPersistableElement.class);
        if (persistable != null) {
            IMemento frameMemento = memento.createChild(TAG_FRAME_INPUT);

            frameMemento.putString(TAG_FACTORY_ID, persistable.getFactoryId());
            persistable.saveState(frameMemento);

            if (expandedElements.length > 0) {
                IMemento expandedMem = memento.createChild(TAG_EXPANDED);
                saveElements(expandedElements, expandedMem);
            }
            if (selection instanceof IStructuredSelection) {
                Object[] elements = ((IStructuredSelection) selection)
                        .toArray();
                if (elements.length > 0) {
                    IMemento selectionMem = memento.createChild(TAG_SELECTION);
                    saveElements(elements, selectionMem);
                }
            }
        }
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public void setExpandedElements(Object[] expandedElements) {
        this.expandedElements = expandedElements;
    }

    public void setSelection(ISelection selection) {
        this.selection = selection;
    }
}

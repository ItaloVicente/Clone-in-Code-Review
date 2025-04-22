package org.eclipse.ui.internal.dialogs;

import java.util.ArrayList;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.model.AdaptableList;

public class WizardContentProvider implements ITreeContentProvider {

    private AdaptableList input;

    @Override
	public void dispose() {
        input = null;
    }

    @Override
	public Object[] getChildren(Object parentElement) {
        if (parentElement instanceof WizardCollectionElement) {
            ArrayList list = new ArrayList();
            WizardCollectionElement element = (WizardCollectionElement) parentElement;

            Object[] childCollections = element.getChildren();
            for (int i = 0; i < childCollections.length; i++) {
                handleChild(childCollections[i], list);
            }

            Object[] childWizards = element.getWizards();
            for (int i = 0; i < childWizards.length; i++) {
                handleChild(childWizards[i], list);
            }

            if (list.size() == 1
                    && list.get(0) instanceof WizardCollectionElement) {
                return getChildren(list.get(0));
            }

            return list.toArray();
        } else if (parentElement instanceof AdaptableList) {
            AdaptableList aList = (AdaptableList) parentElement;
            Object[] children = aList.getChildren();
            ArrayList list = new ArrayList(children.length);
            for (int i = 0; i < children.length; i++) {
                handleChild(children[i], list);
            }
            if (list.size() == 1
            		&& list.get(0) instanceof WizardCollectionElement) {
                return getChildren(list.get(0));
            }
                
            return list.toArray();
        } else {
			return new Object[0];
		}
    }

    @Override
	public Object[] getElements(Object inputElement) {
        return getChildren(inputElement);
    }

    @Override
	public Object getParent(Object element) {
        if (element instanceof WizardCollectionElement) {
            Object[] children = input.getChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i].equals(element)) {
					return input;
				}
            }
            return ((WizardCollectionElement) element).getParent(element);
        } 
        return null;
    }

    private void handleChild(Object element, ArrayList list) {
        if (element instanceof WizardCollectionElement) {
            if (hasChildren(element)) {
				list.add(element);
			}
        } else {
            list.add(element);
        }
    }

    @Override
	public boolean hasChildren(Object element) {
        if (element instanceof WizardCollectionElement) {
            if (getChildren(element).length > 0) {
				return true;
			}
        }
        return false;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        input = (AdaptableList) newInput;
    }
}

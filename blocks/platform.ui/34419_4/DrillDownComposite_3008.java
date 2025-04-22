package org.eclipse.ui.part;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchMessages;

public class DrillDownAdapter implements ISelectionChangedListener {
    private TreeViewer fChildTree;

    private DrillStack fDrillStack;

    private Action homeAction;

    private Action backAction;

    private Action forwardAction;

    public DrillDownAdapter(TreeViewer tree) {
        fDrillStack = new DrillStack();
        fChildTree = tree;
    }

    public void addNavigationActions(IMenuManager manager) {
        createActions();
        manager.add(homeAction);
        manager.add(backAction);
        manager.add(forwardAction);
        updateNavigationButtons();
    }

    public void addNavigationActions(IToolBarManager toolBar) {
        createActions();
        toolBar.add(homeAction);
        toolBar.add(backAction);
        toolBar.add(forwardAction);
        updateNavigationButtons();
    }

    public boolean canExpand(Object element) {
        return fChildTree.isExpandable(element);
    }

    public boolean canGoBack() {
        return fDrillStack.canGoBack();
    }

    public boolean canGoHome() {
        return fDrillStack.canGoHome();
    }

    public boolean canGoInto() {
        IStructuredSelection oSelection = (IStructuredSelection) fChildTree
                .getSelection();
        if (oSelection == null || oSelection.size() != 1) {
			return false;
		}
        Object anElement = oSelection.getFirstElement();
        return canExpand(anElement);
    }

    private void createActions() {
        if (homeAction != null) {
			return;
		}

        homeAction = new Action(WorkbenchMessages.GoHome_text) {
            @Override
			public void run() {
                goHome();
            }
        };
        homeAction
                .setToolTipText(WorkbenchMessages.GoHome_toolTip);
        homeAction
                .setImageDescriptor(WorkbenchImages
                        .getImageDescriptor(ISharedImages.IMG_ETOOL_HOME_NAV));

        ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
        backAction = new Action(WorkbenchMessages.GoBack_text) {
            @Override
			public void run() {
                goBack();
            }
        };
        backAction
                .setToolTipText(WorkbenchMessages.GoBack_toolTip); 
        backAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_BACK));
        backAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_BACK_DISABLED));

        forwardAction = new Action(WorkbenchMessages.GoInto_text) { 
            @Override
			public void run() {
                goInto();
            }
        };
        forwardAction.setToolTipText(WorkbenchMessages.GoInto_toolTip); 
        forwardAction.setImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
        forwardAction.setDisabledImageDescriptor(images
                .getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD_DISABLED));

        fChildTree.addSelectionChangedListener(this);
        updateNavigationButtons();
    }

    private void expand(List items) {
        fChildTree.setExpandedElements(items.toArray());
    }

    private List getExpanded() {
        return Arrays.asList(fChildTree.getExpandedElements());
    }

    public void goBack() {
        Object currentInput = fChildTree.getInput();
        DrillFrame oFrame = fDrillStack.goBack();
        Object input = oFrame.getElement();
        fChildTree.setInput(input);
        expand(oFrame.getExpansion());
        if (fChildTree.getSelection().isEmpty()) {
			fChildTree
                    .setSelection(new StructuredSelection(currentInput), true);
		}
        updateNavigationButtons();
    }

    public void goHome() {
        Object currentInput = fChildTree.getInput();
        DrillFrame oFrame = fDrillStack.goHome();
        Object input = oFrame.getElement();
        fChildTree.setInput(input);
        expand(oFrame.getExpansion());
        if (fChildTree.getSelection().isEmpty()) {
			fChildTree
                    .setSelection(new StructuredSelection(currentInput), true);
		}
        updateNavigationButtons();
    }

    public void goInto() {
        IStructuredSelection sel = (IStructuredSelection) fChildTree
                .getSelection();
        Object element = sel.getFirstElement();
        goInto(element);
    }

    public void goInto(Object newInput) {
        if (canExpand(newInput)) {
            Object oldInput = fChildTree.getInput();
            List expandedList = getExpanded();
            fDrillStack.add(new DrillFrame(oldInput, "null", expandedList));//$NON-NLS-1$

            fChildTree.setInput(newInput);
            expand(expandedList);
            updateNavigationButtons();
        }
    }

    public void reset() {
        fDrillStack.reset();
        updateNavigationButtons();
    }

    @Override
	public void selectionChanged(SelectionChangedEvent event) {
        updateNavigationButtons();
    }

    protected void updateNavigationButtons() {
        if (homeAction != null) {
            homeAction.setEnabled(canGoHome());
            backAction.setEnabled(canGoBack());
            forwardAction.setEnabled(canGoInto());
        }
    }
}

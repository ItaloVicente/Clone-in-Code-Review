
package org.eclipse.ui.tests.leaks;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.part.EditorPart;

public class ContextEditorPart extends EditorPart {

	private Text text;
	private ISelectionProvider selectionProvider = null;
	private Menu contextMenu;

	@Override
	public void doSave(IProgressMonitor arg0) {

	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite arg0, IEditorInput arg1) {
		setSite(arg0);
		setInput(arg1);
		selectionProvider = new ISelectionProvider() {
			@Override
			public void addSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			@Override
			public ISelection getSelection() {
				return new StructuredSelection("Hi there");
			}

			@Override
			public void removeSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			@Override
			public void setSelection(ISelection selection) {
			}
		};
		getSite().setSelectionProvider(selectionProvider);
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {
		text = new Text(parent, SWT.MULTI | SWT.WRAP);
		text.setText("Hi there");
		MenuManager manager = new MenuManager();
		manager.setRemoveAllWhenShown(true);
		contextMenu = manager.createContextMenu(text);
		text.setMenu(contextMenu);

		getSite().registerContextMenu(manager, selectionProvider);
	}

	@Override
	public void setFocus() {
		text.setFocus();
	}

	public void showMenu() {
		contextMenu.notifyListeners(SWT.Show, null);
	}

	public void hideMenu() {
		contextMenu.notifyListeners(SWT.Hide, null);
	}

	public Menu getMenu() {
		return contextMenu;
	}
}

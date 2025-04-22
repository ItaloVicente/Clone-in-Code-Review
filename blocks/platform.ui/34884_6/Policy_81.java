package org.eclipse.ui.internal.navigator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.internal.navigator.extensions.INavigatorSiteEditor;
import org.eclipse.ui.navigator.CommonViewer;


public class NavigatorSiteEditor implements INavigatorSiteEditor {

	private Tree navigatorTree;
	private TreeEditor treeEditor;
	private Text textEditor;
	private Composite textEditorParent;
	private TextActionHandler textActionHandler;
	private String text; // the text being edited
	private CommonViewer commonViewer;


	public NavigatorSiteEditor(CommonViewer aCommonViewer, Tree navigatorTree) {
		commonViewer = aCommonViewer;
		this.navigatorTree = navigatorTree;
		treeEditor = new TreeEditor(navigatorTree);
	}

	Composite createParent() {
		Composite result = new Composite(navigatorTree, SWT.NONE);
		TreeItem[] selectedItems = navigatorTree.getSelection();
		treeEditor.horizontalAlignment = SWT.LEFT;
		treeEditor.grabHorizontal = true;
		treeEditor.setEditor(result, selectedItems[0]);
		return result;
	}

	void createTextEditor(final Runnable runnable) {
		textEditorParent = createParent();
		textEditorParent.setVisible(false);
		textEditorParent.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Point textSize = textEditor.getSize();
				Point parentSize = textEditorParent.getSize();
				e.gc.drawRectangle(0, 0, Math.min(textSize.x + 4, parentSize.x - 1), parentSize.y - 1);
			}
		});

		textEditor = new Text(textEditorParent, SWT.NONE);
		textEditorParent.setBackground(textEditor.getBackground());
		textEditor.addListener(SWT.Modify, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Point textSize = textEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				textSize.x += textSize.y; // Add extra space for new characters.
				Point parentSize = textEditorParent.getSize();
				textEditor.setBounds(2, 1, Math.min(textSize.x, parentSize.x - 4), parentSize.y - 2);
				textEditorParent.redraw();
			}
		});
		textEditor.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.detail) {
					case SWT.TRAVERSE_ESCAPE :
						disposeTextWidget();
						event.doit = true;
						event.detail = SWT.TRAVERSE_NONE;
						break;
					case SWT.TRAVERSE_RETURN :
						saveChangesAndDispose(runnable);
						event.doit = true;
						event.detail = SWT.TRAVERSE_NONE;
						break;
				}
			}
		});
		textEditor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent fe) {
				saveChangesAndDispose(runnable);
			}
		});

		if (textActionHandler != null) {
			textActionHandler.addText(textEditor);
		}
	}

	void disposeTextWidget() {
		if (textActionHandler != null) {
			textActionHandler.removeText(textEditor);
		}
		if (textEditorParent != null) {
			textEditorParent.dispose();
			textEditorParent = null;
			textEditor = null;
			treeEditor.setEditor(null, null);
		}
	}

	@Override
	public void edit(Runnable runnable) {
		IStructuredSelection selection = (IStructuredSelection) commonViewer.getSelection();

		if (selection.size() != 1) {
			return;
		}
		text = getLabel(selection.getFirstElement());
		if (text == null) {
			return;
		}
		if (textEditorParent == null) {
			createTextEditor(runnable);
		}
		textEditor.setText(text);
		textEditorParent.setVisible(true);
		Point textSize = textEditor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		textSize.x += textSize.y; // Add extra space for new characters.
		Point parentSize = textEditorParent.getSize();
		textEditor.setBounds(2, 1, Math.min(textSize.x, parentSize.x - 4), parentSize.y - 2);
		textEditorParent.redraw();
		textEditor.selectAll();
		textEditor.setFocus();
	}

	String getLabel(Object element) {
		return ((ILabelProvider) commonViewer.getLabelProvider()).getText(element);
	}

 
	@Override
	public String getText() {
		return text;
	}

	void saveChangesAndDispose(final Runnable runnable) {
		final String newText = textEditor.getText();
		Runnable editRunnable = new Runnable() {
			@Override
			public void run() {
				disposeTextWidget();
				if (newText.length() > 0 && newText.equals(text) == false) {
					text = newText;
					runnable.run();
				}
				text = null;
			}
		};
		navigatorTree.getShell().getDisplay().asyncExec(editRunnable);
	}

 
	@Override
	public void setTextActionHandler(TextActionHandler actionHandler) {
		textActionHandler = actionHandler;
	}

}

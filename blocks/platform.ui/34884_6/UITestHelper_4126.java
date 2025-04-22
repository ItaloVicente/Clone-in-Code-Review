package org.eclipse.ui.tests.browser.internal;

import java.text.MessageFormat;
import java.util.Iterator;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.eclipse.core.runtime.IAdaptable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.PropertyDialog;
import org.eclipse.ui.internal.dialogs.PropertyPageContributorManager;
import org.eclipse.ui.internal.dialogs.PropertyPageManager;
import org.eclipse.ui.model.IWorkbenchAdapter;

public class UITestHelper {
	private static class PreferenceDialogWrapper extends PreferenceDialog {
		public PreferenceDialogWrapper(Shell parentShell, PreferenceManager manager) {
			super(parentShell, manager);
		}
		protected boolean showPage(IPreferenceNode node) {
			return super.showPage(node);
		}
	}
	
	private static class PropertyDialogWrapper extends PropertyDialog {
		public PropertyDialogWrapper(Shell parentShell, PreferenceManager manager, ISelection selection) {
			super(parentShell, manager, selection);
		}
		protected boolean showPage(IPreferenceNode node) {
			return super.showPage(node);
		}
	}

	protected static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

	public static PreferenceDialog getPreferenceDialog(String id) {
		PreferenceDialogWrapper dialog = null;
		PreferenceManager manager = WorkbenchPlugin.getDefault().getPreferenceManager();
		if (manager != null) {
			dialog = new PreferenceDialogWrapper(getShell(), manager);
			dialog.create();	

			for (Iterator iterator = manager.getElements(PreferenceManager.PRE_ORDER).iterator();
			     iterator.hasNext();)
			{
				IPreferenceNode node = (IPreferenceNode)iterator.next();
				if ( node.getId().equals(id) ) {
					dialog.showPage(node);
					break;
				}
			}
		}
		return dialog;
	}
	
	public static PropertyDialog getPropertyDialog(String id, IAdaptable element) {
		PropertyDialogWrapper dialog = null;

		PropertyPageManager manager = new PropertyPageManager();
		String title = "";
		String name  = "";

		PropertyPageContributorManager.getManager().contribute(manager, element);
		
		IWorkbenchAdapter adapter = (IWorkbenchAdapter)element.getAdapter(IWorkbenchAdapter.class);
		if (adapter != null) {
			name = adapter.getLabel(element);
		}
		
		Iterator pages = manager.getElements(PreferenceManager.PRE_ORDER).iterator();		
		if (!pages.hasNext())
			return null;
		
		title = MessageFormat.format("PropertyDialog.propertyMessage", new Object[] {name});
		dialog = new PropertyDialogWrapper(getShell(), manager, new StructuredSelection(element)); 
		dialog.create();
		dialog.getShell().setText(title);
		for (Iterator iterator = manager.getElements(PreferenceManager.PRE_ORDER).iterator();
		     iterator.hasNext();) {
			IPreferenceNode node = (IPreferenceNode)iterator.next();
			if ( node.getId().equals(id) ) {
				dialog.showPage(node);
				break;
			}
		}
		return dialog;
	}
	
	public static void assertDialog(Dialog dialog) {
		Assert.assertNotNull(dialog);
		dialog.setBlockOnOpen(false);
		dialog.open();
		Shell shell = dialog.getShell();
		verifyCompositeText(shell);
		dialog.close();
	}

	private static void verifyCompositeText(Composite composite) {
		Control children[] = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			try {
				verifyButtonText((Button) children[i]);
			} catch (ClassCastException exNotButton) {
				try {
					verifyLabelText((Label) children[i]);
				} catch (ClassCastException exNotLabel) {
					try {
						verifyCompositeText((Composite) children[i]);
					} catch (ClassCastException exNotComposite) {
					}
				}
			}
		}
	}
	
	private static void verifyButtonText(Button button) {
		String widget = button.toString();
		Point size = button.getSize();

		Point preferred = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (preferred.y * size.y > 0) {
			preferred.y /= countLines(button.getText()); //check for '\n\'
			if (size.y / preferred.y > 1) {
				preferred.x /= (size.y / preferred.y);
			}
		}

		String message =
			new StringBuffer("Warning: ")
				.append(widget)
				.append("\n\tActual Width -> ")
				.append(size.x)
				.append("\n\tRecommended Width -> ")
				.append(preferred.x)
				.toString();
		if (preferred.x > size.x) {
			button.getShell().dispose();
			Assert.assertTrue(message.toString(), false);
		}
	}
	
	private static void verifyLabelText(Label label) {
		String widget = label.toString();
		Point size = label.getSize();

		Point preferred = label.computeSize(SWT.DEFAULT, SWT.DEFAULT);
		if (preferred.y * size.y > 0) {
			preferred.y /= countLines(label.getText());
			if (size.y / preferred.y > 1) {
				preferred.x /= (size.y / preferred.y);
			}
		}
		String message = new StringBuffer("Warning: ").append(widget)
			.append("\n\tActual Width -> ").append(size.x)
			.append("\n\tRecommended Width -> ").append(preferred.x).toString();
		if (preferred.x > size.x) {
			label.getShell().dispose();
			Assert.assertTrue(message.toString(), false);
		}
	}
	
	private static int countLines(String text) {
		int newLines = 1;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				newLines++;
			}
		}
		return newLines;
	}
}

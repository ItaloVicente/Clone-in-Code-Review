package org.eclipse.jface.snippets.resources;

import java.net.URL;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Snippet057FileImageDescriptors {
	private ImageRegistry registry;

	public Snippet057FileImageDescriptors(final Shell shell) {

		Dialog dia = new Dialog(shell) {
			private ImageDescriptor getImageDescriptorFromClass(String path) {
				ImageDescriptor desc = getDescriptorBasedOn(shell, path);
				if (desc == null) {
					desc = ImageDescriptor.createFromFile(
							Snippet057FileImageDescriptors.class, path);
					registry.put(path, desc);
				}

				return desc;
			}

			private ImageDescriptor getImageDescriptorFromFile(String path) {
				ImageDescriptor desc = getDescriptorBasedOn(shell, path);
				if (desc == null) {
					URL classPath = Snippet057FileImageDescriptors.class
							.getResource(path);
					Class<?> bogus = null;
					desc = ImageDescriptor.createFromFile(bogus,
							classPath.getFile());

					registry.put(path, desc);
				}
				return desc;
			}

			private ImageDescriptor getDescriptorBasedOn(final Shell shell,
					String path) {
				if (registry == null) {
					registry = new ImageRegistry(shell.getDisplay());
				}
				ImageDescriptor desc = registry.getDescriptor(path);
				return desc;
			}

			@Override
			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				Button b = super.createButton(parent, id, label, defaultButton);
				if (id == IDialogConstants.OK_ID) {
					b.setImage(getImageDescriptorFromClass("filesave.png").createImage()); //$NON-NLS-1$
					setButtonLayoutData(b);
				} else {
					b.setImage(getImageDescriptorFromFile("cancel.png").createImage()); //$NON-NLS-1$
					setButtonLayoutData(b);
					return b;
				}

				return b;
			}
		};
		dia.open();
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		shell.open();

		new Snippet057FileImageDescriptors(shell);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}

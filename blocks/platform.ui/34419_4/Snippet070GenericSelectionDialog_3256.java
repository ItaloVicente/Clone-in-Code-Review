package org.eclipse.jface.snippets.dialogs;

import java.net.MalformedURLException;
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

public class Snippet060DialogWithURLImageButtons {
	private ImageRegistry registry;

	public Snippet060DialogWithURLImageButtons(final Shell shell) {

		Dialog dia = new Dialog(shell) {
			private ImageDescriptor getImageDescriptor(String path) {
				if (registry == null) {
					registry = new ImageRegistry(shell.getDisplay());
				}

				ImageDescriptor desc = registry.getDescriptor(path);
				if (desc == null) {

					desc = ImageDescriptor
							.createFromURL(Snippet060DialogWithURLImageButtons.class
									.getResource(path));
					registry.put(path, desc);
				}

				return desc;
			}

			protected Button createButton(Composite parent, int id,
					String label, boolean defaultButton) {
				Button b = super.createButton(parent, id, label, defaultButton);
				if (id == IDialogConstants.OK_ID) {

					URL url;
					try {
						url = new URL(
								"http://www.eclipse.org/home/images/enterprise.gif"); //$NON-NLS-N$
						b.setImage(ImageDescriptor.createFromURL(url)
								.createImage());
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}

					setButtonLayoutData(b);
				} else {
					b.setImage(getImageDescriptor("cancel.png").createImage()); //$NON-NLS-1$
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

		new Snippet060DialogWithURLImageButtons(shell);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}
}

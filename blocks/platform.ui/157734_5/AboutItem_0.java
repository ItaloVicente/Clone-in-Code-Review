package org.eclipse.e4.ui.internal.dialogs.about;

import java.util.Optional;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.dialogs.textbundles.E4DialogMessages;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AboutDialogE4 extends TrayDialog {

	private final int maxImageWidth = 250;

	private final ProductInformation product;

	private StyledText textWidget;

	private AboutText aboutText;

	public AboutDialogE4(final Shell parentShell) {
		super(parentShell);
		product = new ProductInformation(Optional.ofNullable(Platform.getProduct()));
	}

	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(NLS.bind(E4DialogMessages.AboutDialog_shellTitle, product.getName()));

	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		Image aboutImage = null;
		AboutItem item = null;
		if (product != null) {
			aboutImage = product.getAboutImage();
			if (aboutImage == null || aboutImage.getBounds().width <= maxImageWidth) {
				String aboutTextProperty = product.getAboutText();
				if (aboutTextProperty != null) {
					aboutText = new AboutText(aboutTextProperty);
					item = aboutText.getAboutItem();
				}
			}
		}

		Composite workArea = WidgetFactory.composite(SWT.NONE).layoutData(new GridData(GridData.FILL_BOTH))
				.create(parent);
		GridLayoutFactory.fillDefaults().applyTo(workArea);

		Color background = JFaceColors.getBannerBackground(parent.getDisplay());
		Color foreground = JFaceColors.getBannerForeground(parent.getDisplay());
		Composite top = (Composite) super.createDialogArea(workArea);

		GridLayoutFactory.fillDefaults().applyTo(top);
		top.setLayoutData(new GridData(GridData.FILL_BOTH));
		top.setBackground(background);
		top.setForeground(foreground);

		final Composite topContainer = WidgetFactory.composite(SWT.NONE).background(background).foreground(foreground)
				.create(top);
		GridLayoutFactory.fillDefaults().numColumns(aboutImage == null || item == null ? 1 : 2).applyTo(topContainer);

		int topContainerHeightHint = calculateTopContainerHeightAndCreateImage(aboutImage, background, foreground,
				topContainer,
				parent);
		GridDataFactory.fillDefaults().grab(true, true).hint(SWT.DEFAULT, topContainerHeightHint).applyTo(topContainer);

		createTextWidget(parent, item, background, foreground, topContainer);

		return workArea;
	}

	private void createTextWidget(final Composite parent, AboutItem item, Color background, Color foreground,
			final Composite topContainer) {
		if (item != null) {
			final Composite textComposite = WidgetFactory.composite(SWT.NONE).background(background)
					.create(topContainer);

			GridDataFactory.fillDefaults().grab(true, true).hint(400, SWT.DEFAULT).applyTo(textComposite);
			GridLayoutFactory.fillDefaults().applyTo(textComposite);

			textWidget = new StyledText(textComposite,
					SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
			textWidget.setFont(parent.getFont());
			textWidget.setText(item.getText());
			textWidget.setBackground(background);
			textWidget.setForeground(foreground);
			textWidget.setAlwaysShowScrollBars(false);

			aboutText = new AboutText(textWidget, item);

			createTextMenu();

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, true).applyTo(textWidget);
			textComposite.layout();
		}
	}

	private int calculateTopContainerHeightAndCreateImage(Image aboutImage, Color background,
			Color foreground,
			final Composite topContainer, Composite parent) {
		GC gc = new GC(parent);

		int topContainerHeightHint = 100;
		try {
			topContainerHeightHint = Math.max(topContainerHeightHint, gc.getFontMetrics().getHeight() * 6);
		} finally {
			gc.dispose();
		}

		if (aboutImage != null) {
			Label imageLabel = WidgetFactory.label(SWT.NONE).background(background).foreground(foreground)
					.image(aboutImage).create(topContainer);
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).applyTo(imageLabel);
			topContainerHeightHint = Math.max(topContainerHeightHint, aboutImage.getBounds().height);
		}
		return topContainerHeightHint;
	}

	private void createTextMenu() {
		final MenuManager popupManager = new MenuManager();

		textWidget.setMenu(popupManager.createContextMenu(textWidget));
		textWidget.addDisposeListener(e -> {
			popupManager.dispose();
		});

	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}

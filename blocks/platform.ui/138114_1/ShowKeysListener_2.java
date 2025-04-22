package org.eclipse.ui.internal.keys.show;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.Geometry;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ShortKeysPopup extends Window {

	private static final String POPUP_COLOR_BG = PlatformUI.PLUGIN_ID + ".showkeys.backgroundColor"; //$NON-NLS-1$
	private static final String POPUP_COLOR_FG = PlatformUI.PLUGIN_ID + ".showkeys.foregroundColor"; //$NON-NLS-1$
	private static final int POPUP_FONT_SIZEFACTOR_KEY_LABEL = 2;
	private static final int POPUP_FONT_SIZEFACTOR_KEY = POPUP_FONT_SIZEFACTOR_KEY_LABEL + 1;
	private static final int MARGIN_BOTTOM = 25;
	private final String keysPageId = "org.eclipse.ui.preferencePages.Keys"; //$NON-NLS-1$

	private final List<Resource> resources = new ArrayList<>(3);
	private final int timeToClose;
	private String shortcut;
	private String shortcutText;
	private String shortcutDescription;
	private boolean readyToClose = true;

	public ShortKeysPopup(Shell parentShell, int timeToClose) {
		super(parentShell);
		this.timeToClose = timeToClose;
		setShellStyle((SWT.NO_TRIM | SWT.ON_TOP | SWT.TOOL) & ~SWT.APPLICATION_MODAL);
	}

	public void setShortcut(String shortcut, String shortcutText, String shortcutDescription) {
		this.shortcut = shortcut;
		this.shortcutText = shortcutText;
		this.shortcutDescription = shortcutDescription;
	}

	@Override
	public int open() {
		scheduleClose();

		Shell shell = getShell();
		if (shell == null || shell.isDisposed()) {
			shell = null;
			create();
			shell = getShell();
		}

		constrainShellSize();

		shell.setVisible(true);

		return OK;
	}

	private void scheduleClose() {
		this.readyToClose = true;
		Display.getDefault().timerExec(this.timeToClose, () -> {
			if (ShortKeysPopup.this.readyToClose && getShell() != null && !getShell().isDisposed()) {
				close();
			}
		});
	}

	@Override
	public boolean close() {
		boolean closed = super.close();

		for (Resource resource : this.resources) {
			resource.dispose();
		}
		this.resources.clear();

		return closed;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		Color color = JFaceResources.getColorRegistry().get(POPUP_COLOR_BG);
		newShell.setBackground(color);
		newShell.setAlpha(170);
	}

	@Override
	protected Control createContents(Composite parent) {
		Font font = JFaceResources.getDialogFont();
		FontData[] defaultFontData = font.getFontData();
		Color foregroundColor = JFaceResources.getColorRegistry().get(POPUP_COLOR_FG);

		Composite contents = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(contents);
		contents.setBackground(parent.getBackground());
		hookDoubleClickListener(contents);

		if (shortcut != null) {
			Label shortcutLabel = new Label(contents, SWT.CENTER);
			GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(shortcutLabel);
			FontData fontData = new FontData(defaultFontData[0].getName(),
					defaultFontData[0].getHeight() * POPUP_FONT_SIZEFACTOR_KEY, SWT.BOLD);
			Font shortcutFont = new Font(getShell().getDisplay(), fontData);
			this.resources.add(shortcutFont);
			shortcutLabel.setBackground(parent.getBackground());
			shortcutLabel.setForeground(foregroundColor);
			shortcutLabel.setFont(shortcutFont);
			shortcutLabel.setText(shortcut + " – " + shortcutText); //$NON-NLS-1$
			hookDoubleClickListener(shortcutLabel);
		}

		if (shortcutDescription != null) {
			Label shortcutDescriptionLabel = new Label(contents, SWT.CENTER);
			GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.CENTER).applyTo(shortcutDescriptionLabel);
			FontData fontData = new FontData(defaultFontData[0].getName(), (int) (defaultFontData[0].getHeight() * 1.3),
					SWT.NORMAL);
			Font shortcutFont = new Font(getShell().getDisplay(), fontData);
			this.resources.add(shortcutFont);
			shortcutDescriptionLabel.setFont(shortcutFont);
			shortcutDescriptionLabel.setBackground(parent.getBackground());
			shortcutDescriptionLabel.setForeground(foregroundColor);
			shortcutDescriptionLabel.setText(shortcutDescription);
			hookDoubleClickListener(shortcutDescriptionLabel);
		}

		return contents;
	}

	private void hookDoubleClickListener(Control control) {
		control.addListener(SWT.MouseDoubleClick, (e) -> {
			PreferencesUtil.createPreferenceDialogOn(getParentShell(), keysPageId, null, null).open();
		});
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		Composite parent = getShell().getParent();
		Rectangle parentBounds = parent.getBounds();

		Monitor monitor = parent.getMonitor();
		Rectangle monitorBounds = monitor.getClientArea();

		Point centerPoint = Geometry.centerPoint(parent.getBounds());

		return new Point(centerPoint.x - (initialSize.x / 2), //
				Math.max(monitorBounds.y, //
						parentBounds.y + parentBounds.height - (initialSize.y) - MARGIN_BOTTOM));
	}
}

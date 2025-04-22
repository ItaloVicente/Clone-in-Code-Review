package org.eclipse.jface.tests.viewers.interactive;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;


public class TestApplicationWindow extends ApplicationWindow {

	public static void main(String[] args) {
		new TestApplicationWindow().open();
	}

	public TestApplicationWindow() {
		super(null);
		setBlockOnOpen(true);
		addMenuBar();
	}

	@Override
	protected Point getInitialLocation(Point initialSize) {
		return new Point(30, 30);
	}

	@Override
	protected Point getInitialSize() {
		return new Point(150, 150);
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);

		fillMenuBar();

		return contents;
	}

	private void fillMenuBar() {
		MenuManager menuManager = getMenuBarManager();
		MenuManager fileMenu = new MenuManager("&File");
		menuManager.add(fileMenu);

		Action loadAction = new Action("&Save") {
			@Override
			public void run() {
			}
		};
		fileMenu.add(loadAction);
		menuManager.updateAll(false);
	}

}

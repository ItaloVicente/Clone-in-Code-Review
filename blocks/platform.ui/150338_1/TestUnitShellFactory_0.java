package org.eclipse.jface.widgets;

import java.util.function.Function;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class ShellFactory extends AbstractCompositeFactory<ShellFactory, Shell> {

	private ShellFactory(int style) {
		super(ShellFactory.class, (Composite parent) -> new Shell((Shell) parent, style));
	}

	public static ShellFactory createShell(int style) {
		return new ShellFactory(style);
	}

	public ShellFactory text(String text) {
		addProperty(shell -> shell.setText(text));
		return this;
	}

	public ShellFactory minimized(boolean minimized) {
		addProperty(shell -> shell.setMinimized(minimized));
		return this;
	}

	public ShellFactory maximized(boolean maximized) {
		addProperty(shell -> shell.setMaximized(maximized));
		return this;
	}

	public ShellFactory fullScreen(boolean fullScreen) {
		addProperty(shell -> shell.setFullScreen(fullScreen));
		return this;
	}

	public ShellFactory menuBar(Function<Shell, Menu> menuFunction) {
		addProperty(shell -> shell.setMenuBar(menuFunction.apply(shell)));
		return this;
	}

}

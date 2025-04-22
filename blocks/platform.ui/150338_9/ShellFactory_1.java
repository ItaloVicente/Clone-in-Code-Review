package org.eclipse.jface.widgets;

import java.util.function.Consumer;

import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public final class ShellFactory extends AbstractCompositeFactory<ShellFactory, Shell> {

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

	public ShellFactory menuBar(MenuFunction menuFunction) {
		addProperty(shell -> shell.setMenuBar(menuFunction.createMenu(shell)));
		return this;
	}

	public ShellFactory onActivate(Consumer<ShellEvent> consumer) {
		addProperty(shell -> shell.addShellListener(ShellListener.shellActivatedAdapter(consumer)));
		return this;
	}

	public ShellFactory onDeactivate(Consumer<ShellEvent> consumer) {
		addProperty(shell -> shell.addShellListener(ShellListener.shellDeactivatedAdapter(consumer)));
		return this;
	}

	public ShellFactory onIconify(Consumer<ShellEvent> consumer) {
		addProperty(shell -> shell.addShellListener(ShellListener.shellIconifiedAdapter(consumer)));
		return this;
	}

	public ShellFactory onDeiconify(Consumer<ShellEvent> consumer) {
		addProperty(shell -> shell.addShellListener(ShellListener.shellDeiconifiedAdapter(consumer)));
		return this;
	}

	public ShellFactory onClose(Consumer<ShellEvent> consumer) {
		addProperty(shell -> shell.addShellListener(ShellListener.shellClosedAdapter(consumer)));
		return this;
	}
}

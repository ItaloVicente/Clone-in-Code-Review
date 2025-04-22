			shell.addShellListener(ShellListener.shellClosedAdapter(e -> {
				e.doit = false;
				MWindow window = (MWindow) e.widget.getData(OWNING_ME);
				IWindowCloseHandler closeHandler = window.getContext().get(IWindowCloseHandler.class);
				if (closeHandler == null || closeHandler.close(window)) {
					cleanUp(window);

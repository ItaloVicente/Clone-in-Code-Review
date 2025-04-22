		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				try {
					Shell[] shells = Display.getDefault().getShells();
					Shell active = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

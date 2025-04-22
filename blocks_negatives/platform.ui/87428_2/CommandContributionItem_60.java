							display.asyncExec(new Runnable() {
								@Override
								public void run() {
									menuService.releaseContributions(menuManager);
									menuManager.dispose();
								}

				notifyListeners();
			};
			if (Display.getCurrent() != null) {
				task.run();
			} else {
				display.asyncExec(task);
			}

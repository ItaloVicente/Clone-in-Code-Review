		} else {
			Shell shell = curCtrl.getShell();
			if (shell.getData(DragAndDropUtil.IGNORE_AS_DROP_TARGET) != null) {
				System.out.println("Found control ignored as drop target!");
			}
		}

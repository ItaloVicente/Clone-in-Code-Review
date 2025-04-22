			if (coolBarManager.find(IWorkbenchActionConstants.MB_ADDITIONS) != null) {
				coolBarManager.insertAfter(IWorkbenchActionConstants.MB_ADDITIONS, new GroupMarker(
						IWorkbenchActionConstants.GROUP_EDITOR));
			} else {
				coolBarManager.add(new GroupMarker(IWorkbenchActionConstants.GROUP_EDITOR));
			}

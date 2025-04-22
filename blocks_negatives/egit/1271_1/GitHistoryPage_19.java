					if (IFile.class.isAssignableFrom(getInput().getClass())) {
						popupMgr.add(new Separator());
						if (size == 1) {
							popupMgr.add(compareAction);
						} else if (size == 2) {
							popupMgr.add(compareVersionsAction);
						}
						if (size >= 1)
							popupMgr.add(viewVersionsAction);

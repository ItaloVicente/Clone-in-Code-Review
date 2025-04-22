				BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {

					@Override
					public void run() {
						tree.setCheckedElements(treeContentProvider.getElements(tree.getInput()));
						setSubtreeChecked((IContainer)tree.getInput(), false, false);
					}

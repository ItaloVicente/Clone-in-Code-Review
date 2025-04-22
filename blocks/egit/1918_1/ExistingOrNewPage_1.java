			public void checkStateChanged(CheckStateChangedEvent event) {
				if (event.getChecked()) {
					ProjectAndRepo checkable = (ProjectAndRepo)event.getElement();
					for (TreeItem ti : tree.getItems()) {
						if (ti.getItemCount() > 0 ||
								((ProjectAndRepo)ti.getData()).getRepo().equals("")) //$NON-NLS-1$
							ti.setChecked(false);
						for(TreeItem subTi : ti.getItems()) {
							IProject project = ((ProjectAndRepo)subTi.getData()).getProject();
							if (checkable.getProject() != null
									&& !subTi.getData().equals(checkable)
									&& checkable.getProject().equals(project))
								subTi.setChecked(false);
						}

				@Override
				public void widgetSelected(SelectionEvent e) {

					final String refName = ref.getLeaf().getName();

					Job job = new Job(NLS.bind(UIText.RepositoriesView_CheckingOutMessage, refName)) {

						@Override
						protected IStatus run(IProgressMonitor monitor) {

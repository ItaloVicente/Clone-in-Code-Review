					TreeItem[] items = ((Tree) event.widget).getSelection();
					List<IProject> selectedProjects = Stream.of(items).map(TreeItem::getData)
							.filter(IProject.class::isInstance).map(IProject.class::cast).collect(toList());

					boolean allOpen = selectedProjects.stream().allMatch(IProject::isOpen);
					boolean allClosed = selectedProjects.stream().noneMatch(IProject::isOpen);
					Display display = event.widget.getDisplay();
					Shell shell = !display.isDisposed() ? display.getActiveShell() : null;

					if (selectedProjects.isEmpty() || (!allOpen && !allClosed) || shell == null) {

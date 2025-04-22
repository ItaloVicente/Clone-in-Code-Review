		fTreeViewer.addDoubleClickListener(event -> {
			IStructuredSelection selection = (IStructuredSelection) event
					.getSelection();

			Object[] checkedElements = fTreeViewer.getCheckedElements();

			List<Object> unselectedElements = Arrays
					.stream(selection.toArray()).filter(el -> Arrays
							.stream(checkedElements).anyMatch(el::equals))
					.collect(Collectors.toList());


			Object[] selectedElements = Stream
					.of(checkedElements, selection.toArray())
					.flatMap(Stream::of)
					.distinct()
					.toArray(Object[]::new);
			fTreeViewer.setCheckedElements(selectedElements);
			for (Object element : unselectedElements) {
				fTreeViewer.setChecked(element, false);
			}
		});

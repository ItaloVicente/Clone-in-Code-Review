			if (collection.isEmpty())
				return false;

			Object firstElement = collection.iterator().next();
			IStructuredSelection selection;
			if (collection.size() == 1 && firstElement instanceof ITextSelection) {
				selection = SelectionUtils.getStructuredSelection((ITextSelection) firstElement);
			} else {
				selection = new StructuredSelection(new ArrayList<Object>(
						collection));
			}

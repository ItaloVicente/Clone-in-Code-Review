				String[] ar = new String[((IStructuredSelection) viewer
						.getSelection()).toArray().length];
				List<String> selectionList = ((IStructuredSelection) viewer
						.getSelection()).toList();
				String[] selection = selectionList.toArray(ar);
				doRemove(selection, viewer.getTable().getSelectionIndices());

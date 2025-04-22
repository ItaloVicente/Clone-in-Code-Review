				_viewer.setExpandedState(projectChildren[i].getData(), true);

				TreeItem[] srcChildren = projectChildren[i].getItems();
				for (int j = 0; j < srcChildren.length; j++) {
					if (srcChildren[j].getText().startsWith(
							"Compressed Libraries"))


			@Override
			public Object[] getElements(Object inputElement) {
				Object[] elements = super.getElements(inputElement);
				if (allowBare) {
					return elements;
				}
				List<Object> result = new ArrayList<>();
				for (Object element : elements) {
					if (element instanceof RepositoryTreeNode) {
						RepositoryTreeNode node = (RepositoryTreeNode) element;
						if (node.getRepository() != null
								&& !node.getRepository().isBare()) {
							result.add(element);
						}
					}
				}
				return result.toArray();
			}


			if (parentNode.type == Type.FOLDER) {
				List<PathNodeAdapter> deletedChildren = rightPathsWithChildren
						.get(parent);
				if (deletedChildren != null)
					for (PathNodeAdapter path : deletedChildren)
						children.add(path.pathNode);

			}

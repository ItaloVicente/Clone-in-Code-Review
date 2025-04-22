			if (parentNode.type == Type.FOLDER) {
				List<PathNodeAdapter> deletedChildren = compareVersionPathsWithChildren
						.get(parent);
				if (deletedChildren != null)
					for (PathNodeAdapter path : deletedChildren)
						children.add(path.pathNode);

			}

		case PROJ:

			File file = (File) node.getObject();

			for (IProject proj : ResourcesPlugin.getWorkspace().getRoot()
					.getProjects()) {
				if (proj.getLocation().equals(new Path(file.getAbsolutePath()))) {
					return getDecoratedImage(image);
				}
			}
			return image;


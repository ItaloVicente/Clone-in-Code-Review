				IContainer container = root.getProject(obj.getName());
				if (container == null)
					container = root.getFolder(obj.getLocation()
							.makeRelativeTo(root.getLocation()));

				return container;

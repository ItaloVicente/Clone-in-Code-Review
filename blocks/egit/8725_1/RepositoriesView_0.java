				if (resource != null) {
					IPath location = resource.getLocation();
					if (location != null)
						paths.add(location);
				} else if (element instanceof IPath)
					paths.add((IPath) element);

				}
			}
		}
	}

	IContainer createContainersFor(IPath path) throws CoreException {

		IContainer currentFolder = destinationContainer;

		int segmentCount = path.segmentCount();

		if (segmentCount == 0) {

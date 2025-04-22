			IResource[] results = new IResource[count];
			for (int i = 0; i < count; i++) {
				results[i] = readResource(in);
			}
			return results;
		} catch (IOException e) {
			return null;
		}
	}

	private IResource readResource(DataInputStream dataIn) throws IOException {
		int type = dataIn.readInt();
		String path = dataIn.readUTF();
		switch (type) {
		case IResource.FOLDER:
			return workspace.getRoot().getFolder(new Path(path));
		case IResource.FILE:
			return workspace.getRoot().getFile(new Path(path));
		case IResource.PROJECT:
			return workspace.getRoot().getProject(path);
		}
		throw new IllegalArgumentException(
				"Unknown resource type in ResourceTransfer.readResource"); //$NON-NLS-1$
	}

	private void writeResource(DataOutputStream dataOut, IResource resource)
			throws IOException {
		dataOut.writeInt(resource.getType());
		dataOut.writeUTF(resource.getFullPath().toString());
	}

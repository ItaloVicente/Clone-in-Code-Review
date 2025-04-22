            IResource[] results = new IResource[count];
            for (int i = 0; i < count; i++) {
                results[i] = readResource(in);
            }
            return results;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Reads a resource from the given stream.
     *
     * @param dataIn the input stream
     * @return the resource
     * @exception IOException if there is a problem reading from the stream
     */
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
    }

    /**
     * Writes the given resource to the given stream.
     *
     * @param dataOut the output stream
     * @param resource the resource
     * @exception IOException if there is a problem writing to the stream
     */
    private void writeResource(DataOutputStream dataOut, IResource resource)
            throws IOException {
        dataOut.writeInt(resource.getType());
        dataOut.writeUTF(resource.getFullPath().toString());
    }

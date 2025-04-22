            /* read number of markers */
            int n = in.readInt();

            /* read markers */
            IMarker[] markers = new IMarker[n];
            for (int i = 0; i < n; i++) {
                IMarker marker = readMarker(in);
                if (marker == null) {
                    return null;
                }
                markers[i] = marker;
            }
            return markers;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Reads and returns a single marker from the given stream.
     *
     * @param dataIn the input stream
     * @return the marker
     * @exception IOException if there is a problem reading from the stream
     */
    private IMarker readMarker(DataInputStream dataIn) throws IOException {
        /**
         * Marker serialization format is as follows:
         * (String) path of resource for marker
         * (int) marker ID
         */
        String path = dataIn.readUTF();
        long id = dataIn.readLong();
        return findMarker(path, id);
    }

    /**
     * Writes the given marker to the given stream.
     *
     * @param marker the marker
     * @param dataOut the output stream
     * @exception IOException if there is a problem writing to the stream
     */
    private void writeMarker(IMarker marker, DataOutputStream dataOut)
            throws IOException {
        /**
         * Marker serialization format is as follows:
         * (String) path of resource for marker
         * (int) marker ID
         */

        dataOut.writeUTF(marker.getResource().getFullPath().toString());
        dataOut.writeLong(marker.getId());
    }

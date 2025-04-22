        /**
         * Transfer data is an array of markers.  Serialized version is:
         * (int) number of markers
         * (Marker) marker 1
         * (Marker) marker 2
         * ... repeat last four for each subsequent marker
         * see writeMarker for the (Marker) format.
         */
        Object[] markers = (Object[]) object;
        lazyInit(markers);

        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteOut);

        byte[] bytes = null;

        try {
            /* write number of markers */
            out.writeInt(markers.length);

            /* write markers */
            for (Object marker : markers) {
                writeMarker((IMarker) marker, out);
            }
            out.close();
            bytes = byteOut.toByteArray();
        } catch (IOException e) {
        }

        if (bytes != null) {
            super.javaToNative(bytes, transferData);
        }
    }

    /**
     * Initializes the transfer mechanism if necessary.
     */
    private void lazyInit(Object[] markers) {
        if (workspace == null) {
            if (markers != null && markers.length > 0) {
                this.workspace = ((IMarker) markers[0]).getResource()
                        .getWorkspace();
            }
        }
    }

    @Override

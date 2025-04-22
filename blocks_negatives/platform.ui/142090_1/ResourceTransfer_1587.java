        if (!(data instanceof IResource[])) {
            return;
        }

        IResource[] resources = (IResource[]) data;
        /**
         * The resource serialization format is:
         *  (int) number of resources
         * Then, the following for each resource:
         *  (int) resource type
         *  (String) path of resource
         */

        int resourceCount = resources.length;

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(out);

            dataOut.writeInt(resourceCount);

            for (IResource resource : resources) {
                writeResource(dataOut, resource);
            }

            dataOut.close();
            out.close();
            byte[] bytes = out.toByteArray();
            super.javaToNative(bytes, transferData);
        } catch (IOException e) {
        }
    }

    @Override

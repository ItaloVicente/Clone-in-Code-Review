        /**
         * The resource serialization format is:
         *  (int) number of resources
         * Then, the following for each resource:
         *  (int) resource type
         *  (String) path of resource
         */

        byte[] bytes = (byte[]) super.nativeToJava(transferData);
        if (bytes == null) {

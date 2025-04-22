            String str0 = getCompareString(arg0);
            String str1 = getCompareString(arg1);
            return str0.compareTo(str1);
        }

        /**
         * @param object
         * @return <code>String</code> representation of the object.
         */
        private String getCompareString(Object object) {
            if (object instanceof String) {

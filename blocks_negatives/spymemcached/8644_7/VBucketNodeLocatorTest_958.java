    private static final String configInEnvelope =
            "{ \"otherKeyThatIsIgnored\": 12345,\n" +
                    "\"vBucketServerMap\": \n" +
                    "{\n" +
                    "  \"hashAlgorithm\": \"CRC\",\n" +
                    "  \"numReplicas\": 2,\n" +
                    "  \"serverList\": [\"127.0.0.1:11211\", \"127.0.0.1:11210\", \"127.0.0.1:11212\"],\n" +
                    "  \"vBucketMap\":\n" +
                    "    [\n" +
                    "      [0, 1, 2],\n" +
                    "      [1, 2, 0],\n" +
                    "      [2, 1, -1],\n" +
                    "      [1, 2, 0]\n" +
                    "    ]\n" +
                    "}" +
                    "}";

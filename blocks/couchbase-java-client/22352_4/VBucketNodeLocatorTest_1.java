  private static final String NO_REPLICA_CONFIG_IN_ENVELOPE =
      "{ \"otherKeyThatIsIgnored\": 12345,\n"
      + "\"nodes\": [\n"
      + "{\n"
      + "\"clusterCompatibility\": 1,\n"
      + "\"clusterMembership\": \"active\"\n,"
      + "\"couchApiBase\": \"http://10.2.1.67:5984/\"\n"
      + "}\n"
      + "],\n"
      + "\"vBucketServerMap\": \n"
      + "{\n"
      + "  \"hashAlgorithm\": \"CRC\",\n"
      + "  \"numReplicas\": 0,\n"
      + "  \"serverList\": [\"127.0.0.1:11211\", \"127.0.0.1:11210\"],\n"
      + "  \"vBucketMap\":\n" + "    [\n" + "      [-1],\n"
      + "      [-1],\n" + "      [0],\n" + "      [0]\n"
      + "    ]\n" + "}" + "}";


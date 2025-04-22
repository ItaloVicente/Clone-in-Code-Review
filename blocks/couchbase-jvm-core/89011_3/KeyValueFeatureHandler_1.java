    static byte[] generateAgentJson(String agent, long coreId, long channelId) throws Exception {
        String id = paddedHex(coreId) + "/" + paddedHex(channelId);
        if (agent.length() > 200) {
            agent = agent.substring(0, 200);
        }

        HashMap<String, String> result = new HashMap<String, String>();
        result.put("a", agent);
        result.put("i", id);
        return JACKSON.writeValueAsBytes(result);
    }

    private static String paddedHex(long number) {
        return String.format("%016x", number).toUpperCase();
    }


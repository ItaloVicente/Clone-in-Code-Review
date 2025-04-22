    static String generateAgentJson(String agent, long coreId, long channelId) {
        String id = paddedHex(coreId) + "/" + paddedHex(channelId);
        if (agent.length() > 200) {
            agent = agent.substring(0, 200);
        }
        return "{\"a\":\"" + agent + "\",\"i\":\"" + id + "\"}";
    }

    private static String paddedHex(long number) {
        String s = Long.toHexString(number).toUpperCase();
        int p = 16; // preferred length
        for (int g=0,j=s.length(); g < p - j; g++) {
            s = "0" + s;
        }
        return s;
    }


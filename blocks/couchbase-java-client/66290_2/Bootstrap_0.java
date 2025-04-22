
        DirContext ctx;
        if (nameServerIP == null || nameServerIP.isEmpty()) {
            ctx = new InitialDirContext(DNS_ENV);
        } else {
            Hashtable<String, String> finalEnv = new Hashtable<String, String>(DNS_ENV);
            finalEnv.put("java.naming.provider.url", "dns://" + nameServerIP);
            ctx = new InitialDirContext(finalEnv);
        }

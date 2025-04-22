        final String pass;
        if (password == null) {
            String[][] creds = credentialsManager().getCredentials(AuthenticationContext.BUCKET_KEYVALUE, name);
            if (creds != null && creds.length == 1 && creds[0] != null && creds[0].length == 2 && creds[0][1] != null) {
                pass = creds[0][1];
            } else {
                pass = "";
            }
        } else {
            pass = password;
        }

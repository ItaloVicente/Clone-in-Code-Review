    public N1qlParams withCredentials(List<Credential> credentials) {
        for (Credential credential : credentials) {
            withCredentials(credential.login(), credential.password());
        }
        return this;
    }

    public N1qlParams withCredentials(String login, String password) {
        credentials.put(login, password);
        return this;
    }


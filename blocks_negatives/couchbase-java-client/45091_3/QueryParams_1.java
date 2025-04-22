    /**
     * Adds a bucket authentication credential to the list of credentials for the request.
     *
     * @param bucket the authenticated bucket name.
     * @param password the password for the bucket.
     * @return this {@link QueryParams} for chaining.
     */
    public QueryParams addCredential(String bucket, String password) {
        if (this.creds == null) {
            this.creds = JsonArray.empty();
        }
        this.creds.add(JsonObject.create()
            .put("user", "local:" + bucket)
            .put("pass", password));
        return this;
    }

    /** Adds an admin authentication credential to the list of credentials for the request.
     *
     * @param adminName the login of the administrator.
     * @param password the password for the administrator.
     * @return this {@link QueryParams} for chaining.
     */
    public QueryParams addAdminCredential(String adminName, String password) {
        if (this.creds == null) {
            this.creds = JsonArray.empty();
        }
        this.creds.add(JsonObject.create()
                                 .put("user", "admin:" + adminName)
                                 .put("pass", password));
        return this;
    }


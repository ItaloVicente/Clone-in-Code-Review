    @Override
    public Observable<Boolean> upsertUser(final String userid, final UserSettings userSettings) {
        final String payload = getUserSettingsPayload(userSettings);
        return ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<UpsertUserResponse>>() {
                            @Override
                            public Observable<UpsertUserResponse> call(Boolean aBoolean) {
                                return core.send(new UpsertUserRequest(userid, payload, username, password));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<UpsertUserResponse, Boolean>() {
                            @Override
                            public Boolean call(UpsertUserResponse response) {
                                if (!response.status().isSuccess()) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append("Could not update user: ");
                                    sb.append(response.status());
                                    if (response.message().length() > 0) {
                                        sb.append(", ");
                                        sb.append("msg: ");
                                        sb.append(response.message());
                                    }
                                    throw new CouchbaseException(sb.toString());
                                }
                                return true;
                            }
                        });
    }

    @Override
    public Observable<Boolean> removeUser(final String userid) {
        return ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<RemoveUserResponse>>() {
                            @Override
                            public Observable<RemoveUserResponse> call(Boolean aBoolean) {
                                return core.send(new RemoveUserRequest(userid, username, password));
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<RemoveUserResponse, Boolean>() {
                            @Override
                            public Boolean call(RemoveUserResponse response) {
                                return response.status().isSuccess();
                            }
                        });
    }

    @Override
    public Observable<User> getUsers() {
        return ensureServiceEnabled()
                .flatMap(new Func1<Boolean, Observable<GetUsersResponse>>() {
                    @Override
                    public Observable<GetUsersResponse> call(Boolean aBoolean) {
                        return core.send(new GetUsersRequest(username, password));
                    }
                })
                .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                .doOnNext(new Action1<GetUsersResponse>() {
                    @Override
                    public void call(GetUsersResponse response) {
                        if (!response.status().isSuccess()) {
                            if (response.content().contains("Unauthorized")) {
                                throw new InvalidPasswordException();
                            } else {
                                throw new CouchbaseException(response.status() + ": " + response.content());
                            }
                        }
                    }
                })
                .flatMap(new Func1<GetUsersResponse, Observable<User>>() {
                    @Override
                    public Observable<User> call(GetUsersResponse response) {
                        try {
                            JsonArray decoded = CouchbaseAsyncBucket.JSON_ARRAY_TRANSCODER.stringToJsonArray(response.content());
                            List<User> users = new ArrayList<User>();
                            for (Object item : decoded) {
                                JsonObject userJsonObj = (JsonObject) item;
                                JsonArray rolesJsonArr = userJsonObj.getArray("roles");
                                UserRole[] userRoles = new UserRole[rolesJsonArr.size()];
                                int i = 0;
                                for (Object role:rolesJsonArr) {
                                    userRoles[i] = new UserRole(((JsonObject)role).getString("role"), ((JsonObject)role).getString("bucket_name"));
                                    i++;
                                }
                                User user = new User();
                                user.name(userJsonObj.getString("name"));
                                user.userId(userJsonObj.getString("id"));
                                user.type(userJsonObj.getString("type"));
                                user.roles(userRoles);
                                users.add(user);
                            }
                            return Observable.from(users);
                        } catch (Exception e) {
                            throw new TranscodingException("Could not decode cluster info.", e);
                        }
                    }
                });
    }


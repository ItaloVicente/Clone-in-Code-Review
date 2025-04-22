    @Test
    public void testUpsertUser() {
        Observable
                .just(USER_1)
                .map(new Func1<String, UserSettings>() {
                    @Override
                    public UserSettings call(final String username) {
                        return UserSettings
                                .build()
                                .name(username)
                                .password("password")
                                .roles(Collections.singletonList(new UserRole("data_reader", "*")));
                    }
                }).flatMap(new Func1<UserSettings, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(final UserSettings userSettings) {
                return clusterManager.async().upsertUser(userSettings.name(), userSettings);
            }
        }).toBlocking().single();
        clusterManager.removeUser(USER_1);
    }

    @Test(expected = CouchbaseException.class)
    public void testUpsertUserMissingPassword() {
        Observable
                .just(USER_2)
                .map(new Func1<String, UserSettings>() {
                    @Override
                    public UserSettings call(final String username) {
                        return UserSettings
                                .build()
                                .name(username)
                                .roles(Collections.singletonList(new UserRole("data_reader", "*")));
                    }
                }).flatMap(new Func1<UserSettings, Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call(final UserSettings userSettings) {
                return clusterManager.async().upsertUser(userSettings.name(), userSettings);
            }
        }).toBlocking().single();
    }


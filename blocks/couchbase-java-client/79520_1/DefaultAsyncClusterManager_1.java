                                User user = new User(decoded.getString("name"), decoded.getString("id"),
                                        decoded.getString("domain"), userRoles);
                                return Observable.just(user);
                            } else {
                                JsonArray decoded = CouchbaseAsyncBucket.JSON_ARRAY_TRANSCODER.stringToJsonArray(response.content());
                                List<User> users = new ArrayList<User>();
                                for (Object item : decoded) {
                                    JsonObject userJsonObj = (JsonObject) item;
                                    JsonArray rolesJsonArr = userJsonObj.getArray("roles");
                                    UserRole[] userRoles = new UserRole[rolesJsonArr.size()];
                                    int i = 0;
                                    for (Object role : rolesJsonArr) {
                                        userRoles[i] = new UserRole(((JsonObject) role).getString("role"), ((JsonObject) role).getString("bucket_name"));
                                        i++;
                                    }
                                    User user = new User(userJsonObj.getString("name"), userJsonObj.getString("id"),
                                            userJsonObj.getString("domain"), userRoles);
                                    users.add(user);
                                }
                                return Observable.from(users);

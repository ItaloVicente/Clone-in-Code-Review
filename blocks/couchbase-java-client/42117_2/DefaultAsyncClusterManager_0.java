
                            int ramQuota = 0;
                            if (bucket.getObject("quota").get("ram") instanceof Long) {
                                ramQuota = (int) (bucket.getObject("quota").getLong("ram") / 1024 / 1024);
                            } else {
                                ramQuota = bucket.getObject("quota").getInt("ram") / 1024 / 1024;
                            }


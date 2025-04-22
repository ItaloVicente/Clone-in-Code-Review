                    if (hasQuery) {
                        path.append("?").append(queryMsg.query());
                        if (hasKeys) {
                            path.append("&keys=").append(encodeKeysGet(queryMsg.keys()));
                        }
                    } else {
                        path.append("?keys=").append(encodeKeysGet(queryMsg.keys()));
                    }

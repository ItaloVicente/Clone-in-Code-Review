                                String serverName = value.getString("name");
                                if (prepared.preparedName() != null && !prepared.preparedName().equals(serverName)) {
                                    throw new IllegalStateException("Prepared statement name from server differs: " +
                                            serverName + ", expected " + prepared.preparedName());
                                }
                                return new PreparedPayload(prepared.originalStatement(), prepared.preparedName());

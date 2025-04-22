            if (remoteUri == null) {
                try {
                    URIish candidateUri = new URIish(remote);
                    for (String candidateRemote : repoConfig.getSubsections(
                            ConfigConstants.CONFIG_REMOTE_SECTION)) {
                        URIish foundUri = new URIish(repoConfig.getString(
            			        ConfigConstants.CONFIG_REMOTE_SECTION
                                candidateRemote
            					ConfigConstants.CONFIG_KEY_URL));
                        if (matches(candidateUri
                            remote = candidateRemote;
                            remoteUri = foundUri.toString();
                            break;
                        }
                    }
                } catch(URISyntaxException e) {
                }
            }


                if (response.content().refCnt() == 0 || response.content() == null) {
                    throw new ConfigurationException("Successful config from node, but content not readable or empty: "
                        + response);
                }


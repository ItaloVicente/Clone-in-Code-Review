
                LOGGER.debug("Successfully loaded config through carrier.");
                String content = response.content().toString(CharsetUtil.UTF_8);
                response.content().release();
                return replaceHostWildcard(content, hostname);
            }
        });

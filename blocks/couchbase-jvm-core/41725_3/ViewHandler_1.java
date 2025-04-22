                Tuple2<String, String> splitIfPostNeeded = extractKeysFromQueryString(
                        queryMsg.query(), MAX_GET_LENGTH);
                if (splitIfPostNeeded.value2() == null) {
                    path.append("?").append(queryMsg.query());
                } else {
                    method = HttpMethod.POST;
                    path.append('?').append(splitIfPostNeeded.value1());
                    content = ctx.alloc().buffer(splitIfPostNeeded.value2().length());
                    content.writeBytes(splitIfPostNeeded.value2().getBytes(CHARSET));
                }

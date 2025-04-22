                Tuple2<String, String> splitIfPostNeeded = extractKeysFromQueryString(
                        queryMsg.query(), MAX_GET_LENGTH);
                if (splitIfPostNeeded.value2() == null) {
                    path.append("?").append(queryMsg.query());
                } else {
                    method = HttpMethod.POST;
                    path.append('?').append(splitIfPostNeeded.value1());
                    content = Unpooled.copiedBuffer(splitIfPostNeeded.value2(), CHARSET);
                }

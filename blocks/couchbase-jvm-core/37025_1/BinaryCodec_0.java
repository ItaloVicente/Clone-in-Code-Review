            in.add(
                new GetBucketConfigResponse(
                    convertStatus(msg.getStatus()),
                    msg.content().toString(CharsetUtil.UTF_8),
                    addr.getHostName()
                )
            );

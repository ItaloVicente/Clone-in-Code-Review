                    new GetBucketConfigResponse(
                            convertStatus(msg.getStatus()),
                            bucket,
                            msg.content().copy(),
                            InetAddress.getByName(address.getHostName())
                    )

            int flags = 0;
            if (msg.getExtrasLength() > 0)
            {
                final ByteBuf extras = Unpooled.copiedBuffer(msg.getExtras());
                flags = extras.getInt(0);
                extras.release();
            }
            in.add(new GetResponse(status, cas, flags, bucket, content, currentRequest));

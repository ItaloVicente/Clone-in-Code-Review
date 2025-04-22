            ByteBuf content;
            if (selectedMechanism == "CRAM-MD5") {
                String[] evaluated = new String(evaluatedBytes).split(" ");
                content = Unpooled.copiedBuffer(username + "\0" + evaluated[1], CharsetUtil.UTF_8);
            } else {
                content = Unpooled.wrappedBuffer(evaluatedBytes);
            }

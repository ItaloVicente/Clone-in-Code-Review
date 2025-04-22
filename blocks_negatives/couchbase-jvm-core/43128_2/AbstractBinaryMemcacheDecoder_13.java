            case READ_HEADER: try {
                if (in.readableBytes() < 24) {
                    return;
                }
                resetDecoder();

                currentMessage = decodeHeader(in);
                state = State.READ_EXTRAS;
            } catch (Exception e) {
                out.add(invalidMessage(e));
                return;
            }
            case READ_EXTRAS: try {
                byte extrasLength = currentMessage.getExtrasLength();
                if (extrasLength > 0) {
                    if (in.readableBytes() < extrasLength) {


                state = State.READ_KEY;
            } catch (Exception e) {
                out.add(invalidMessage(e));
                return;
            }
            case READ_KEY: try {
                short keyLength = currentMessage.getKeyLength();
                if (keyLength > 0) {
                    if (in.readableBytes() < keyLength) {
                        return;


                out.add(currentMessage);
                state = State.READ_CONTENT;
            } catch (Exception e) {
                out.add(invalidMessage(e));
                return;
            }
            case READ_CONTENT: try {
                int valueLength = currentMessage.getTotalBodyLength()
                    - currentMessage.getKeyLength()
                    - currentMessage.getExtrasLength();
                int toRead = in.readableBytes();
                if (valueLength > 0) {
                    if (toRead == 0) {
                        return;

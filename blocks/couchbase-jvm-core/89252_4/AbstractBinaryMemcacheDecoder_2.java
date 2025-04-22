                    state = State.READ_FRAMING;
                } catch (Exception e) {
                    out.add(invalidMessage(e));
                    return;
                }
            case READ_FRAMING:
                try {
                    byte framingLength = currentMessage.getFramingExtrasLength();
                    if (framingLength > 0) {
                        if (in.readableBytes() < framingLength) {
                            return;
                        }

                        currentMessage.setFramingExtras(readBytes(ctx.alloc(), in, framingLength));
                    }


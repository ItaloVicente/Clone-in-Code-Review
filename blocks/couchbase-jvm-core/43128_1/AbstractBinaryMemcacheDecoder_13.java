            case READ_EXTRAS:
                try {
                    byte extrasLength = currentMessage.getExtrasLength();
                    if (extrasLength > 0) {
                        if (in.readableBytes() < extrasLength) {
                            return;
                        }

                        currentMessage.setExtras(readBytes(ctx.alloc(), in, extrasLength));

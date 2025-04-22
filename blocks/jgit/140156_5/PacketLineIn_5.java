                    switch (arg) {
                        case " continue":
                            return AckNackResult.ACK_CONTINUE;
                        case " common":
                            return AckNackResult.ACK_COMMON;
                        case " ready":
                            return AckNackResult.ACK_READY;
                        default:
                            break;
                    }

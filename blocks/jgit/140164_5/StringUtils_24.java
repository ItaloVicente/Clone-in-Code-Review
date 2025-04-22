                    switch (ch) {
                        case '\r':
                            if (i + 1 < buf.length && in.charAt(i + 1) == '\n') {
                                buf[o++] = ' ';
                                ++i;
                            } else
                                buf[o++] = ' ';
                            break;
                        case '\n':
                            buf[o++] = ' ';
                            break;
                        default:
                            buf[o++] = ch;
                            break;
                    }

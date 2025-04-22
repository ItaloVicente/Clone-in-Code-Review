                    switch (c) {
                        case '\r':
                            if (lastw < i) {
                                out.write(b
                            }
                            lastw = i + 1;
                            buf = '\r';
                            break;
                        case '\n':
                            if (buf == '\r') {
                                out.write('\n');
                                lastw = i + 1;
                                buf = -1;
                            } else {
                                if (lastw < i + 1) {
                                    out.write(b
                                }
                                lastw = i + 1;
                            }
                            break;
                        default:
                            if (buf == '\r') {
                                out.write('\r');
                                lastw = i;
                            }
                            buf = -1;
                            break;
                    }

                    switch (c) {
                        case '\r':
                            buf = '\r';
                            break;
                        case '\n':
                            if (buf != '\r') {
                                if (lastw < i) {
                                    out.write(b
                                }
                                out.write('\r');
                                lastw = i;
                            }
                            buf = -1;
                            break;
                        default:
                            buf = -1;
                            break;
                    }

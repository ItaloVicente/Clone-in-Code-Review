                    OUTER:
                    for (; (ptr + 2) < p; p--) {
                        switch (raw[p]) {
                            case '.':
                                dots++;
                                break;
                            case ' ':
                                space = true;
                                break;
                            default:
                                break OUTER;
                        }
                    }

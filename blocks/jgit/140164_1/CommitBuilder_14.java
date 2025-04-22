                    switch (ch) {
                        case '\r':
                            if (i + 1 < in.length() && in.charAt(i + 1) == '\n') {
                                out.write('\n');
                                out.write(' ');
                                ++i;
                            } else {
                                out.write('\n');
                                out.write(' ');
                            }
                            break;
                        case '\n':
                            out.write('\n');
                            out.write(' ');
                            break;
                        default:
                            if (ch > 127)
                                throw new IllegalArgumentException(MessageFormat
                                        .format(JGitText.get().notASCIIString
                            out.write(ch);
                            break;
                    }

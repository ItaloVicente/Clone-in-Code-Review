                    fBound += buf.length();
                    buf.setLength(0);
                }
                break;
            case '?':
                /* append special character representing single match wildcard */
                buf.append(fSingleWildCard);
                break;
            default:
                buf.append(c);
            }
        }

        /* add last buffer to segment list */
        if (buf.length() > 0) {

                    switch (cnt) {
                        case 0:
                            children = new ReverseCommit[] { c };
                            break;
                        case 1:
                            children = new ReverseCommit[] { c
                            break;
                        default:
                            ReverseCommit[] n = new ReverseCommit[1 + cnt];
                            n[0] = c;
                            System.arraycopy(children
                            children = n;
                            break;
                    }

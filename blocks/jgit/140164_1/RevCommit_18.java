                            switch (nParents) {
                                case 0:
                                    pList[nParents++] = p;
                                    break;
                                case 1:
                                    pList = new RevCommit[] { pList[0]
                                    nParents = 2;
                                    break;
                                default:
                                    if (pList.length <= nParents) {
                                        RevCommit[] old = pList;
                                        pList = new RevCommit[pList.length + 32];
                                        System.arraycopy(old
                                    }
                                    pList[nParents++] = p;
                                    break;
                            }

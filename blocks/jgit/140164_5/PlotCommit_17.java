            switch (cnt) {
                case 0:
                    children = new PlotCommit[] { c };
                    break;
                case 1:
                    if (!c.getId().equals(children[0].getId()))
                        children = new PlotCommit[] { children[0]
                    break;
                default:
                    for (PlotCommit pc : children)
                        if (c.getId().equals(pc.getId()))
                            return;
                    final PlotCommit[] n = new PlotCommit[cnt + 1];
                    System.arraycopy(children
                    n[cnt] = c;
                    children = n;
                    break;
            }

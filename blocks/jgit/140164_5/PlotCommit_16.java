            switch (cnt) {
                case 0:
                    lanes = new PlotLane[] { l };
                    break;
                case 1:
                    lanes = new PlotLane[] { lanes[0]
                    break;
                default:
                    final PlotLane[] n = new PlotLane[cnt + 1];
                    System.arraycopy(lanes
                    n[cnt] = l;
                    lanes = n;
                    break;
            }

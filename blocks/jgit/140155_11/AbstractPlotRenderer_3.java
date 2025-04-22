                    for (PlotLane forkingOffLane : commit.forkingOffLanes) {
                        final TLane childLane = (TLane) forkingOffLane;
                        final TColor cColor = laneColor(childLane);
                        final int cx = laneC(childLane);
                        if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
                            final int ix;
                            if (myLaneX < cx)
                                ix = cx - LANE_WIDTH / 2;
                            else
                                ix = cx + LANE_WIDTH / 2;
                            
                            drawLine(cColor
                            drawLine(cColor
                        } else {
                            drawLine(cColor
                        }
                        maxCenter = Math.max(maxCenter
                    }

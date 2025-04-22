                    for (PlotLane mergingLane : commit.mergingLanes) {
                        final TLane pLane = (TLane) mergingLane;
                        final TColor pColor = laneColor(pLane);
                        final int cx = laneC(pLane);
                        if (Math.abs(myLaneX - cx) > LANE_WIDTH) {
                            final int ix;
                            if (myLaneX < cx)
                                ix = cx - LANE_WIDTH / 2;
                            else
                                ix = cx + LANE_WIDTH / 2;
                            
                            drawLine(pColor
                            drawLine(pColor
                        } else
                            drawLine(pColor
                        maxCenter = Math.max(maxCenter
                    }

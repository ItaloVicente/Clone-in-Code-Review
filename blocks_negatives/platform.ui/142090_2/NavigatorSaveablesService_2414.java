                            for (int m = 0; !foundRoot && m < parentPath.getSegmentCount(); m++) {
                                if (roots.contains(parentPath.getSegment(m))) {
                                    result.add(saveable);
                                    foundRoot = true;
                                }
                            }

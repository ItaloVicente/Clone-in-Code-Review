                if (mouseDown) {
                    if (!dragEvent) {
                        StyledText text = (StyledText) e.widget;
                        text.setCursor(null);
                    }
                    dragEvent = true;
                    return;
                }
                StyledText text = (StyledText) e.widget;
                int offset = -1;
                try {
                    offset = text.getOffsetAtLocation(new Point(e.x, e.y));
                } catch (IllegalArgumentException ex) {
                }
                if (offset == -1) {

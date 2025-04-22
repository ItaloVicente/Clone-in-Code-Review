        gc.drawLine(x+w-1, y, x+w-1, y+h);
		gc.drawLine(x, y+h-1, x+w, y+h-1);

        if (lowMemThreshold != 0 && ((double)(maxMem - usedMem) / (double)maxMem < lowMemThreshold)) {
            gc.setBackground(lowMemCol);
        } else {
            gc.setBackground(usedMemCol);
        }
        gc.fillRectangle(x + 1, y + 1, uw, h - 2);

        gc.setBackground(freeMemCol);
        gc.fillRectangle(ux + 1, y + 1, tx - (ux + 1), h - 2);

        if (showLowMemThreshold && lowMemThreshold != 0) {
            gc.setForeground(lowMemCol);
            int thresholdX = x + 1 + (int) (sw * (1.0 - lowMemThreshold));
            gc.drawLine(thresholdX, y + 1, thresholdX, y + h - 2);
        }

        String s = NLS.bind(WorkbenchMessages.HeapStatus_status,
				convertToMegString(usedMem), convertToMegString(totalMem));
        Point p = gc.textExtent(s);
        int sx = (rect.width - 15 - p.x) / 2 + rect.x + 1;
        int sy = (rect.height - 2 - p.y) / 2 + rect.y + 1;
        gc.setForeground(textCol);
        gc.drawString(s, sx, sy, true);

        if (mark != -1) {
            int ssx = (int) (sw * mark / maxMem) + x + 1;
            paintMark(gc, ssx, y, h);
        }
    }

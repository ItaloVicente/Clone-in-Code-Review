        for (IMarker marker : markers) {
            report += MarkerUtil.getMessage(marker) + '\t';
            report += MarkerUtil.getResourceName(marker) + '\t';
            report += MarkerUtil.getContainerName(marker) + '\t';
            int line = MarkerUtil.getLineNumber(marker);
            report += NLS.bind(BookmarkMessages.LineIndicator_text, String.valueOf(line));
        }

        return report;
    }

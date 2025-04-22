                    sb.append(path.segment(i));
                }
                folderText.setText(sb.toString());
            }
        }

        if (initialAttributes != null) {
            Object description = initialAttributes.get(IMarker.MESSAGE);
            if (description != null && description instanceof String) {

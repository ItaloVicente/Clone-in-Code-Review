        sb.append(number);
        sb.append(' ');
        if (suffix.length() <= MAX_TEXT_LENGTH) {
            sb.append(suffix);
        } else {
            sb.append(suffix.substring(0, MAX_TEXT_LENGTH));
        }
        return sb.toString();
    }

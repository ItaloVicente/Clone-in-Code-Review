        }

        return hashCode;
    }

    public String toString() {
        if (string == null) {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('[');
            stringBuffer.append(activityId);
            stringBuffer.append(',');
            stringBuffer.append(categoryId);
            stringBuffer.append(',');
            stringBuffer.append(sourceId);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }

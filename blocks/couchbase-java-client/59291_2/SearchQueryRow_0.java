                if (addDelim) {
                    sb.append(", ");
                } else {
                    addDelim = true;
                }
                sb.append("\"" + fragment.getKey() + "\":" + JsonArray.from(fragment.getValue()).toString());

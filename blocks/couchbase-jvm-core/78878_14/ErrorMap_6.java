            StringBuilder sb = new StringBuilder();
            sb.append("ErrorCode{");
            sb.append("name='" + name + '\'');
            sb.append(", description='" + description + '\'' );
            sb.append(", attributes=" + attributes);
            if (retrySpec != null) {
                sb.append(", retryHint=" + retrySpec.toString());
            }
            sb.append("}");
            return sb.toString();

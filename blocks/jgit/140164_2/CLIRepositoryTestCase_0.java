            switch (cmds.length) {
                case 0:
                    return "";
                case 1:
                    return "\"" + escapeJava(cmds[0]) + "\"";
                default:
                    StringBuilder sb = new StringBuilder(cmdString(cmds[0]));
                    for (int i=1; i<cmds.length; i++) {
                        sb.append("
                        sb.append(cmdString(cmds[i]));
                    }
                    return sb.toString();
            }

            int closeBracketPos = -1;
            int openBrackets = 0;
            for (int i = responseContent.readerIndex(); i <= responseContent.writerIndex(); i++) {
                byte current = responseContent.getByte(i);
                if (current == '{') {
                    openBrackets++;
                } else if (current == '}' && openBrackets > 0) {
                    openBrackets--;
                    if (openBrackets == 0) {
                        closeBracketPos = i;
                        break;
                    }
                }

            for (CombinedOldImage old1 : old) {
                old1.startLine = -parseBase10(buf
                if (buf[ptr.value] == '
                    old1.lineCount = parseBase10(buf
                } else {
                    old1.lineCount = 1;
                }
            }

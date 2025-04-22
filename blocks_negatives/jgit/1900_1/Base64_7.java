
                lineLength += 4;
                if( breakLines && lineLength == MAX_LINE_LENGTH )
                {
                    outBuff[e+4] = NEW_LINE;
                    e++;
                    lineLength = 0;

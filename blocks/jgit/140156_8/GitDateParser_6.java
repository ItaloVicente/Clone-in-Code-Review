			if (null == parts[i + 1]) 
                            return null;
			else switch (parts[i + 1]) {
                        case "year":
                        case "years":
                            cal.add(Calendar.YEAR
                            break;
                        case "month":
                        case "months":
                            cal.add(Calendar.MONTH
                            break;
                        case "week":
                        case "weeks":
                            cal.add(Calendar.WEEK_OF_YEAR
                            break;
                        case "day":
                        case "days":
                            cal.add(Calendar.DATE
                            break;
                        case "hour":
                        case "hours":
                            cal.add(Calendar.HOUR_OF_DAY
                            break;
                        case "minute":
                        case "minutes":
                            cal.add(Calendar.MINUTE
                            break;
                        case "second":
                        case "seconds":
                            cal.add(Calendar.SECOND
                            break;
                        default:
                            return null;
                    }

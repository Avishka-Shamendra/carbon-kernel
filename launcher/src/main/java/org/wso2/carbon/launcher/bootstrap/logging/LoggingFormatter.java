/*
 *  Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.wso2.carbon.launcher.bootstrap.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Logging formatter of java.util.logging correspond to log4j logging pattern.
 *
 * @since 5.0.0
 */
public class LoggingFormatter extends Formatter {
    // Create a DateFormat to format the logger timestamp.
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");

    /**
     * Convenience method for configuring java.util.logging log format to
     * compatible with configured log4j pattern.
     *
     * @return formatted log message
     */
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append("[").append(df.format(new Date(record.getMillis()))).append("] ");
        builder.append(record.getLevel());
        builder.append(" {").append(record.getSourceClassName()).append("} - ");
        builder.append(formatMessage(record));

        //get any throwable associated with the log record.
        //This will be the exception object if the log record involved in an exception.
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        builder.append(throwable).append("\n");
        return builder.toString();
    }

}

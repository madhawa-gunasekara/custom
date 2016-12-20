/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.integrator.json;

import org.apache.axiom.om.OMOutputFormat;
import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.MessageFormatter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;

public class JsonStreamFormatter implements MessageFormatter {

    Method synapseFormatter_getBytesMethod;
    Method axis2GsonFormatter_getBytesMethod;
    Method synapseFormatter_writeToMethod;
    Method axis2GsonFormatter_writeToMethod;
    Method synapseFormatter_getContentTypeMethod;
    Method axis2GsonFormatter_getContentTypeMethod;
    Method synapseFormatter_getTargetAddressMethod;
    Method axis2GsonFormatter_getTargetAddressMethod;
    Method synapseFormatter_formatSOAPActionMethod;
    Method axis2GsonFormatter_formatSOAPActionMethod;
    Object synapseFormatter;
    Object axis2GsonFormatter;

    private static final Log logger = LogFactory.getLog(JsonStreamFormatter.class.getName());

    public JsonStreamFormatter()
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Class synapseFormatterClass = JsonStreamFormatter.class.getClassLoader().loadClass("org.apache.synapse.commons.json.JsonStreamFormatter");
        this.synapseFormatter = synapseFormatterClass.newInstance();
        this.synapseFormatter_getBytesMethod = synapseFormatterClass.getMethod("getBytes", new Class[]{MessageContext.class, OMOutputFormat.class});
        this.synapseFormatter_writeToMethod = synapseFormatterClass.getMethod("writeTo", new Class[]{MessageContext.class, OMOutputFormat.class, OutputStream.class, boolean.class});
        this.synapseFormatter_getContentTypeMethod = synapseFormatterClass.getMethod("getContentType", new Class[]{MessageContext.class, OMOutputFormat.class, String.class});
        this.synapseFormatter_getTargetAddressMethod = synapseFormatterClass.getMethod("getTargetAddress", new Class[]{MessageContext.class, OMOutputFormat.class, URL.class});
        this.synapseFormatter_formatSOAPActionMethod = synapseFormatterClass.getMethod("formatSOAPAction", new Class[]{MessageContext.class, OMOutputFormat.class, String.class});


        Class axis2GsonFormatterClass = JsonStreamFormatter.class.getClassLoader().loadClass("org.apache.axis2.json.gson.JsonFormatter");
        this.axis2GsonFormatter = axis2GsonFormatterClass.newInstance();
        this.axis2GsonFormatter_getBytesMethod = axis2GsonFormatterClass.getMethod("getBytes", new Class[]{MessageContext.class, OMOutputFormat.class});
        this.axis2GsonFormatter_writeToMethod = axis2GsonFormatterClass.getMethod("writeTo", new Class[]{MessageContext.class, OMOutputFormat.class, OutputStream.class, boolean.class});
        this.axis2GsonFormatter_getContentTypeMethod = axis2GsonFormatterClass.getMethod("getContentType", new Class[]{MessageContext.class, OMOutputFormat.class, String.class});
        this.axis2GsonFormatter_getTargetAddressMethod = axis2GsonFormatterClass.getMethod("getTargetAddress", new Class[]{MessageContext.class, OMOutputFormat.class, URL.class});
        this.axis2GsonFormatter_formatSOAPActionMethod = axis2GsonFormatterClass.getMethod("formatSOAPAction", new Class[]{MessageContext.class, OMOutputFormat.class, String.class});
    }

    public byte[] getBytes(MessageContext messageContext, OMOutputFormat omOutputFormat) throws AxisFault {
        try {
            if (Utils.isDataService(messageContext)) {
                return (byte[]) axis2GsonFormatter_getBytesMethod.invoke(axis2GsonFormatter, messageContext, omOutputFormat);
            } else {
                return (byte[]) synapseFormatter_getBytesMethod.invoke(axis2GsonFormatter, messageContext, omOutputFormat);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AxisFault(e.getMessage());
        }
    }

    public void writeTo(MessageContext messageContext, OMOutputFormat omOutputFormat, OutputStream outputStream,
                        boolean b) throws AxisFault {
        try {
            if (Utils.isDataService(messageContext)) {
                axis2GsonFormatter_writeToMethod.invoke(axis2GsonFormatter, messageContext, omOutputFormat, outputStream, b);
            } else {
                synapseFormatter_writeToMethod.invoke(synapseFormatter, messageContext, omOutputFormat, outputStream, b);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AxisFault(e.getMessage());
        }
    }

    public String getContentType(MessageContext messageContext, OMOutputFormat omOutputFormat, String s) {
        try {
            if (Utils.isDataService(messageContext)) {
                return (String) axis2GsonFormatter_getContentTypeMethod.invoke(axis2GsonFormatter, messageContext, omOutputFormat, s);
            } else {
                return (String) synapseFormatter_getContentTypeMethod.invoke(synapseFormatter, messageContext, omOutputFormat, s);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public URL getTargetAddress(MessageContext messageContext, OMOutputFormat omOutputFormat, URL url)
            throws AxisFault {
        try {
            if (Utils.isDataService(messageContext)) {
                return (URL) axis2GsonFormatter_getTargetAddressMethod.invoke(axis2GsonFormatter, messageContext, omOutputFormat, url);
            } else {
                return (URL) synapseFormatter_getTargetAddressMethod.invoke(synapseFormatter, messageContext, omOutputFormat, url);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new AxisFault(e.getMessage());
        }
    }

    public String formatSOAPAction(MessageContext messageContext, OMOutputFormat omOutputFormat, String s) {
        try {
            if (Utils.isDataService(messageContext)) {
                return (String) axis2GsonFormatter_formatSOAPActionMethod.invoke(axis2GsonFormatter, messageContext, omOutputFormat, s);
            } else {
                return (String) synapseFormatter_formatSOAPActionMethod.invoke(synapseFormatter, messageContext, omOutputFormat, s);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}

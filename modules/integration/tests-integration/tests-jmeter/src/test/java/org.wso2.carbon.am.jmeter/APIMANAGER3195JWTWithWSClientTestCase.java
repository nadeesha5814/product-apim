/*
*Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.am.jmeter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.am.integration.test.utils.base.APIMIntegrationBaseTest;
import org.wso2.carbon.automation.extensions.jmeter.JMeterTest;
import org.wso2.carbon.automation.extensions.jmeter.JMeterTestManager;
import org.wso2.carbon.automation.test.utils.common.TestConfigurationProvider;
import org.wso2.carbon.integration.common.utils.mgt.ServerConfigurationManager;

import java.io.File;

public class APIMANAGER3195JWTWithWSClientTestCase extends APIMIntegrationBaseTest {

    private ServerConfigurationManager serverConfigurationManager;
    private Log log = LogFactory.getLog(getClass());

    @BeforeClass(alwaysRun = true)
    public void configServer() throws Exception {
        super.init();

        String apiManagerXml = getAMResourceLocation() + File.separator +
                               "configFiles" + File.separator + "jwt_wsclient_config" + File.separator + "api-manager.xml";

        serverConfigurationManager = new ServerConfigurationManager(gatewayContextWrk);
        serverConfigurationManager.applyConfiguration(new File(apiManagerXml));


        super.init();
    }

    @Test(groups = {"wso2.am"}, description = "Create APIs and subscribe")
    public void createAndSubscribeForAPI() throws Exception {

        JMeterTest script =
                new JMeterTest(new File(TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts"
                        + File.separator + "AM" + File.separator + "scripts"
                        + File.separator + "APICreateSubscribeInvoke.jmx"));

        JMeterTestManager manager = new JMeterTestManager();
        manager.runTest(script);

        log.info("JWTWithWSClientTestCase completed successfully");
    }

    @AfterClass(alwaysRun = true)
    public void destroy() throws Exception {
        if (serverConfigurationManager != null) {
            serverConfigurationManager.restoreToLastConfiguration();
        }
        super.cleanUp();
    }
}
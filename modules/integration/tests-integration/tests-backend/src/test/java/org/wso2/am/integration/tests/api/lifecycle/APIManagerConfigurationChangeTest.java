/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.am.integration.tests.api.lifecycle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.wso2.am.admin.clients.webapp.WebAppAdminClient;
import org.wso2.am.integration.test.utils.base.APIMIntegrationConstants;
import org.wso2.am.integration.test.utils.webapp.WebAppDeploymentUtil;
import org.wso2.carbon.automation.test.utils.common.TestConfigurationProvider;

import java.io.File;

/**
 * Deploy jaxrs_basic webApp and monitoring webApp required to run tests
 * jaxrs_basic - Provides rest backend to run tests
 * APIStatusMonitor - Can be used to retrieve API deployment status in worker and manager nodes
 */
public class APIManagerConfigurationChangeTest extends APIManagerLifecycleBaseTest {
    private static final Log log = LogFactory.getLog(APIManagerConfigurationChangeTest.class);

    @BeforeTest(alwaysRun = true)
    public void startDeployingWebAPPs() throws Exception {
        super.init();

        String BASIC_WEB_APP_FILE_NAME = "jaxrs_basic.war";
        String BASIC_WEB_APP_NAME = "jaxrs_basic";
        String PRODEP1_WEB_APP_FILE_NAME = "name-checkOne.war";
        String PRODEP2_WEB_APP_FILE_NAME = "name-checkTwo.war";
        String PRODEP3_WEB_APP_FILE_NAME = "name-checkThree.war";
        String SANDBOXEP1_WEB_APP_FILE_NAME = "name-check1_SB.war";
        String SANDBOXEP2_WEB_APP_FILE_NAME = "name-check2_SB.war";
        String SANDBOXEP3_WEB_APP_FILE_NAME = "name-check3_SB.war";
        String webAppPath = TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" +
                File.separator + "AM" + File.separator + "lifecycletest" + File.separator;

        String basicWebappSourcePath = webAppPath + BASIC_WEB_APP_FILE_NAME;
        String webapp1 = webAppPath + PRODEP1_WEB_APP_FILE_NAME;
        String webapp2 = webAppPath + PRODEP2_WEB_APP_FILE_NAME;
        String webapp3 = webAppPath + PRODEP3_WEB_APP_FILE_NAME;
        String webapp1SB = webAppPath + SANDBOXEP1_WEB_APP_FILE_NAME;
        String webapp2SB = webAppPath + SANDBOXEP2_WEB_APP_FILE_NAME;
        String webapp3SB = webAppPath + SANDBOXEP3_WEB_APP_FILE_NAME;

        String pathAPIStatusMonitorWar =
                TestConfigurationProvider.getResourceLocation() + File.separator + "artifacts" +
                        File.separator + "AM" + File.separator + "war" + File.separator +
                        APIMIntegrationConstants.AM_MONITORING_WEB_APP_NAME + ".war";

        String sessionId = createSession(gatewayContextMgt);

        WebAppAdminClient webAppAdminClient = new WebAppAdminClient(
                gatewayContextMgt.getContextUrls().getBackEndUrl(), sessionId);

        webAppAdminClient.uploadWarFile(basicWebappSourcePath);
        webAppAdminClient.uploadWarFile(webapp1);
        webAppAdminClient.uploadWarFile(webapp2);
        webAppAdminClient.uploadWarFile(webapp3);
        webAppAdminClient.uploadWarFile(webapp1SB);
        webAppAdminClient.uploadWarFile(webapp2SB);
        webAppAdminClient.uploadWarFile(webapp3SB);
        webAppAdminClient.uploadWarFile(pathAPIStatusMonitorWar);

        WebAppDeploymentUtil.isWebApplicationDeployed(gatewayContextMgt.getContextUrls().getBackEndUrl(),
                sessionId, BASIC_WEB_APP_NAME);
        WebAppDeploymentUtil.isWebApplicationDeployed(gatewayContextMgt.getContextUrls().getBackEndUrl(),
                sessionId, APIMIntegrationConstants.AM_MONITORING_WEB_APP_NAME);
        WebAppDeploymentUtil.isMonitoringAppDeployed(gatewayContextWrk.getContextUrls().getWebAppURL());
    }

    @AfterTest(alwaysRun = true)
    public void unDeployWebApps() throws Exception {
        //TODO remove webAPPS
    }
}
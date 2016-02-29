/*
 * Copyright (c) 2015​, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

package org.wso2.am.integration.ui.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.am.integration.ui.tests.util.TestUtil;
import org.wso2.carbon.automation.extensions.selenium.BrowserManager;

/*
 * This test should run with the following configuration
 * Configure AM and IS to use in SSO mode
 */
public class APIMANAGER3277SSOLogin extends APIMIntegrationUiTestBase {

	private WebDriver driver;
       private String TEST_DATA_PASSWORD = "admin", TEST_DATA_FULL_USERNAME = "admin@carbon.super",
            USERNAME_FIELD = "username", PASSWORD_FIELD = "pass";
       protected String publisherURL;

	@BeforeClass(alwaysRun = true)
	protected void setEnvironment() throws Exception {
		super.init();
		driver = BrowserManager.getWebDriver();
		publisherURL = getPublisherURL();
	}

	@Test(groups = "wso2.am")
	public void loginToPublisher() throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		// login to publisher
		driver.get(publisherURL + "/site/pages/login.jag");
		// wait until load the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(USERNAME_FIELD)));
		driver.findElement(By.id(USERNAME_FIELD)).clear();
		driver.findElement(By.id(USERNAME_FIELD)).sendKeys(TEST_DATA_FULL_USERNAME);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PASSWORD_FIELD)));
		// this is the password field in IS sso login page
		driver.findElement(By.id(PASSWORD_FIELD)).clear();
		driver.findElement(By.id(PASSWORD_FIELD)).sendKeys(TEST_DATA_PASSWORD);

		driver.findElement(By.cssSelector(".btn")).click();
		// wait until load the page
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Add")));
		// if element present, test it successful
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
        super.cleanUp();
		driver.quit();
	}

}

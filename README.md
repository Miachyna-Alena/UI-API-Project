## UI API Project  
  
The tested application is deployed with a docker.  

| **№** | **Step** | **Expected result** |  
|------------------------------------------------------|------------------------------------------------------|-----------------------------------------------------|  
| 1. | Request to API to get a token according to the variant number. | The token is generated. |  
| 2. | Go to the site. Pass the necessary authorization. Using cookie, pass the token generated in Step 1 (the token parameter). Refresh the page. | Projects page is open. After refreshing the page, the variant number in the footer is correct. |  
| 3. | Go to the Nexage project page. Get the list of tests in JSON/XML format by request to API. | The tests on the first page are sorted by descending date and match those resulting from the API request. |  
| 4. | Return to the previous page in the browser (projects page). Click on +Add. Input a project name and save. To close the add project window call the js-method closePopUp(). Refresh the page. | After saving the project, a successful save message appears. After the method was called, the window for adding a project closed. After refreshing the page, the project appeared in the list. |  
| 5. | Go to the created project page. Add test via API (along with a screenshot of the current page). | The test is displayed without refreshing the page. | 
